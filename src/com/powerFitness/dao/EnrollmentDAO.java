package com.powerFitness.dao;

import com.powerFitness.utils.DBUtils;
import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
	
	/**
     * 특정 수업의 수강 가능 여부를 확인합니다.
     *
     * @param classId     조회할 수업의 아이디
     * @param targetMonth 조회할 대상 월 (yyyy-MM 형식)
     * @return 수강 가능 여부 (true: 가능, false: 불가능)
     * @throws SQLException SQL 예외 발생 시
     */
    public boolean isClassAvailable(String classId, String targetMonth) throws SQLException {
        String query = "SELECT c.classid, c.max_people, COUNT(a.memberid) AS current_enrolled " +
                       "FROM DB2024_Classes c " +
                       "LEFT JOIN DB2024_Attendings a ON c.classid = a.classid AND a.status IN ('수강중', '수강가능') AND MONTH(a.date) = ? " +
                       "WHERE c.classid = ? " +
                       "GROUP BY c.classid " +
                       "HAVING c.max_people > COUNT(a.memberid)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, targetMonth);
            pstmt.setString(2, classId);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // 수강 가능한 경우 true 반환
        }
    }

    /**
     * 수강 가능한 수업 목록을 조회합니다.
     *
     * @param targetMonth 조회할 대상 월 (yyyy-MM 형식)
     * @return 수강 가능한 수업 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<AttendanceDTO> getAvailableClasses(String targetMonth) throws SQLException {
        List<AttendanceDTO> availableClasses = new ArrayList<>();
        String query = "SELECT c.classid, c.class_name, c.max_people, COUNT(a.memberid) AS current_enrolled " +
                       "FROM DB2024_Classes c " +
                       "LEFT JOIN DB2024_Attendings a ON c.classid = a.classid AND a.status IN ('수강중', '수강가능') AND MONTH(a.date) = ? " +
                       "GROUP BY c.classid " +
                       "HAVING c.max_people > COUNT(a.memberid)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, targetMonth);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AttendanceDTO classInfo = new AttendanceDTO();
                classInfo.setClassId(rs.getString("classid"));
                classInfo.setClassName(rs.getString("class_name"));
                classInfo.setMaxCapacity(rs.getInt("max_people"));
                classInfo.setCurrentEnrollment(rs.getInt("current_enrolled"));
                availableClasses.add(classInfo);
            }
        }
        return availableClasses;
    }
    
    /**
     * 수강 신청을 처리하는 트랜잭션 메소드입니다.
     *
     * @param memberId    회원 아이디
     * @param classId     수업 아이디
     * @param targetMonth 수강 월 (yyyy-MM 형식)
     * @return 수강 신청 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    public boolean enrollInClass(String memberId, String classId, String targetMonth) throws SQLException {
        String attendanceQuery = "INSERT INTO DB2024_Attendings (attendid, memberid, payid, classid, date, status) VALUES (?, ?, ?, ?, ?, ?)";
        String paymentQuery = "INSERT INTO DB2024_Payments (payid, pay_fee, status) VALUES (?, ?, ?)";
        String getClassFeeQuery = "SELECT class_fee FROM DB2024_Classes WHERE classid = ?";

        Connection conn = null;
        PreparedStatement attendancePstmt = null;
        PreparedStatement paymentPstmt = null;
        PreparedStatement classFeePstmt = null;

        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 수업 요금 가져오기
            int classFee = 0;
            classFeePstmt = conn.prepareStatement(getClassFeeQuery);
            classFeePstmt.setString(1, classId);
            ResultSet rs = classFeePstmt.executeQuery();
            if (rs.next()) {
                classFee = rs.getInt("class_fee");
            }

            // 결제 정보 삽입
            String payId = generateUniqueId("pay");
            paymentPstmt = conn.prepareStatement(paymentQuery);
            paymentPstmt.setString(1, payId);
            paymentPstmt.setInt(2, classFee);
            paymentPstmt.setString(3, "결제미완료");
            paymentPstmt.executeUpdate();

            // 수강 신청 삽입
            attendancePstmt = conn.prepareStatement(attendanceQuery);
            attendancePstmt.setString(1, generateUniqueId("attend"));
            attendancePstmt.setString(2, memberId);
            attendancePstmt.setString(3, payId);
            attendancePstmt.setString(4, classId);
            attendancePstmt.setString(5, targetMonth + "-01");
            attendancePstmt.setString(6, "수강불가능");
            attendancePstmt.executeUpdate();

            conn.commit(); // 트랜잭션 커밋
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // 트랜잭션 롤백
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (attendancePstmt != null) attendancePstmt.close();
            if (paymentPstmt != null) paymentPstmt.close();
            if (classFeePstmt != null) classFeePstmt.close();
        }
    }
    
    /**
     * 결제 미완료 상태에서의 수강 신청 취소 메소드입니다.
     *
     * @param attendId 수강 아이디
     * @param payId    결제 아이디
     * @return 수강 신청 취소 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    public boolean cancelEnrollmentWithoutPayment(String attendId, String payId) throws SQLException {
        String deleteEnrollmentQuery = "DELETE FROM DB2024_Attendings WHERE attendid = ? AND status = '수강불가능'";
        String deletePaymentQuery = "DELETE FROM DB2024_Payments WHERE payid = ? AND status = '결제미완료'";

        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            boolean isEnrollmentDeleted = executeUpdate(conn, deleteEnrollmentQuery, attendId);
            if (isEnrollmentDeleted) {
                boolean isPaymentDeleted = executeUpdate(conn, deletePaymentQuery, payId);
                if (isPaymentDeleted) {
                    conn.commit(); // 트랜잭션 커밋
                    conn.setAutoCommit(true); // AutoCommit을 다시 true로 설정
                    return true;
                } else {
                    conn.rollback(); // 트랜잭션 롤백
                    conn.setAutoCommit(true); // AutoCommit을 다시 true로 설정
                    return false;
                }
            } else {
                conn.rollback(); // 트랜잭션 롤백
                conn.setAutoCommit(true); // AutoCommit을 다시 true로 설정
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 결제 완료 상태에서의 수강 신청 취소 메소드입니다.
     *
     * @param attendId 수강 아이디
     * @param payId    결제 아이디
     * @return 수강 신청 취소 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    public boolean cancelEnrollmentWithPayment(String attendId, String payId) throws SQLException {
        String updatePaymentStatusQuery = "UPDATE DB2024_Payments SET status = '결제취소', date = CURRENT_DATE WHERE payid = ?";
        String deleteEnrollmentQuery = "DELETE FROM DB2024_Attendings WHERE attendid = ? AND status = '수강가능'";

        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false); // 트랜잭션 시작

            boolean isPaymentUpdated = executeUpdate(conn, updatePaymentStatusQuery, payId);
            if (isPaymentUpdated) {
                boolean isEnrollmentDeleted = executeUpdate(conn, deleteEnrollmentQuery, attendId);
                if (isEnrollmentDeleted) {
                    conn.commit(); // 트랜잭션 커밋
                    conn.setAutoCommit(true); // AutoCommit을 다시 true로 설정
                    return true;
                } else {
                    conn.rollback(); // 트랜잭션 롤백
                    conn.setAutoCommit(true); // AutoCommit을 다시 true로 설정
                    return false;
                }
            } else {
                conn.rollback(); // 트랜잭션 롤백
                conn.setAutoCommit(true); // AutoCommit을 다시 true로 설정
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 공통 업데이트 메소드
    /**
     * 주어진 쿼리를 실행하여 데이터베이스를 업데이트합니다.
     *
     * @param conn  데이터베이스 연결
     * @param query 실행할 쿼리
     * @param param 쿼리에 전달할 매개변수
     * @return 업데이트 결과 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    private boolean executeUpdate(Connection conn, String query, String param) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, param);
            return pstmt.executeUpdate() > 0;
        }
    }
   

    // 결제상태 및 수강상태 업데이트 
    /**
     * 결제와 수강 상태를 업데이트합니다.
     *
     * @param payId          결제 아이디
     * @param paymentStatus  결제 상태
     * @param payType        결제 타입
     * @param payDate        결제 날짜
     * @return 업데이트 결과 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    private boolean updatePaymentAndEnrollmentStatus(String payId, String paymentStatus, String payType, Date payDate) throws SQLException {
        String updatePaymentStatusQuery = "UPDATE DB2024_Payments SET status = ?, pay_type = ?, date = ? WHERE payid = ?";
        String updateEnrollmentStatusQuery = "UPDATE DB2024_Attendings a " +
                                             "JOIN DB2024_Payments p ON a.payid = p.payid " +
                                             "SET a.status = CASE " +
                                             "    WHEN p.status = '결제미완료' OR p.status = '결제취소' THEN '수강불가능' " +
                                             "    WHEN p.status = '결제완료' THEN " +
                                             "        CASE " +
                                             "            WHEN a.date > CURRENT_DATE THEN '수강가능' " +
                                             "            WHEN a.date <= CURRENT_DATE AND DATE_FORMAT(a.date, '%Y-%m') = DATE_FORMAT(CURRENT_DATE, '%Y-%m') THEN '수강중' " +
                                             "            WHEN a.date < CURRENT_DATE AND DATE_FORMAT(a.date, '%Y-%m') < DATE_FORMAT(CURRENT_DATE, '%Y-%m') THEN '수강완료' " +
                                             "        END " +
                                             "END " +
                                             "WHERE a.payid = ?";

        Connection conn = null;
        try {
            conn = DBUtils.getConnection();
            conn.setAutoCommit(false); // 트랜잭션 시작

            // 결제 상태 업데이트
            try (PreparedStatement paymentPstmt = conn.prepareStatement(updatePaymentStatusQuery)) {
                paymentPstmt.setString(1, paymentStatus);
                paymentPstmt.setString(2, payType);
                paymentPstmt.setDate(3, payDate); // 날짜 파라미터 설정
                paymentPstmt.setString(4, payId);
                int paymentUpdateCount = paymentPstmt.executeUpdate();

                // 결제 상태가 성공적으로 업데이트된 경우 수강 상태도 업데이트
                if (paymentUpdateCount > 0) {
                    try (PreparedStatement enrollmentPstmt = conn.prepareStatement(updateEnrollmentStatusQuery)) {
                        enrollmentPstmt.setString(1, payId);
                        enrollmentPstmt.executeUpdate();
                    }
                }

                conn.commit(); // 트랜잭션 커밋
                return paymentUpdateCount > 0;
            } catch (SQLException e) {
                conn.rollback(); // 트랜잭션 롤백
                throw e;
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // 다시 autoCommit을 true로 설정
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /**
     * 결제 승인을 처리하고 결제 및 수강 상태를 업데이트합니다.
     *
     * @param payId    결제 아이디
     * @param payType  결제 타입
     * @param payDate  결제 날짜
     * @return 결제 승인 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    public boolean approvePayment(String payId, String payType, Date payDate) throws SQLException {
        return updatePaymentAndEnrollmentStatus(payId, "결제완료", payType, payDate);
    }
    
    /**
     * 결제 취소를 처리하고 결제 및 수강 상태를 업데이트합니다.
     *
     * @param payId 결제 아이디
     * @return 결제 취소 성공 여부 (true: 성공, false: 실패)
     * @throws SQLException SQL 예외 발생 시
     */
    public boolean cancelPayment(String payId) throws SQLException {
        Date currentDate = new Date(System.currentTimeMillis());
        return updatePaymentAndEnrollmentStatus(payId, "결제취소", null, currentDate);
    }

    /**
     * 고유한 아이디를 생성합니다.
     *
     * @param prefix 아이디에 붙일 접두사
     * @return 생성된 고유한 아이디
     */
    private String generateUniqueId(String prefix) {
        return prefix + System.currentTimeMillis(); // 현재 시간을 기반으로 고유 ID 생성
    }
}


