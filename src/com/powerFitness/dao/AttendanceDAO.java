package com.powerFitness.dao;

import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 수강 내역에 대한 데이터 액세스 객체입니다.
 */
public class AttendanceDAO {
    
    /**
     * PreparedStatement에 매개변수를 설정합니다.
     * @param pstmt PreparedStatement 객체
     * @param params 매개변수 값 배열
     * @throws SQLException SQL 예외
     */
    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * 쿼리를 실행하여 수강 내역을 조회합니다.
     * @param query 실행할 쿼리
     * @param params 쿼리 매개변수
     * @return 수강 내역 목록
     * @throws SQLException SQL 예외
     */
    private List<AttendanceDTO> queryEnrollments(String query, Object... params) throws SQLException {
        List<AttendanceDTO> enrollments = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            setParameters(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                AttendanceDTO attendance = new AttendanceDTO();
                attendance.setAttendId(rs.getString("attendid"));
                attendance.setMemberId(rs.getString("memberid"));
                attendance.setMemberName(rs.getString("member_name"));
                attendance.setClassId(rs.getString("classid"));
                attendance.setClassName(rs.getString("class_name"));
                attendance.setDate(rs.getString("date"));
                attendance.setStatus(rs.getString("attending_status"));
                attendance.setPayId(rs.getString("payid"));
                attendance.setPaymentStatus(rs.getString("payment_status"));
                enrollments.add(attendance);
            }
        }
        return enrollments;
    }

    // 아래 메소드는 모두 뷰, 인덱스 사용 

    /**
     * 회원의 수강 내역을 조회합니다.
     * @param memberId 회원 ID
     * @return 회원의 수강 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<AttendanceDTO> getAttendingByMember(String memberId) throws SQLException {
        String query = "SELECT * FROM DB2024_V_AttendingDetails WHERE memberid = ?";
        return queryEnrollments(query, memberId);
    }

    /**
     * 전체 회원의 수강 목록을 조회합니다. (관리자용)
     * @return 전체 회원의 수강 목록
     * @throws SQLException SQL 예외
     */
    public List<AttendanceDTO> getAllAttending() throws SQLException {
        String query = "SELECT * FROM DB2024_V_AttendingDetails";
        return queryEnrollments(query);
    }

    /**
     * 수업 ID로 필터링하여 수강 목록을 조회합니다. (관리자용)
     * @param classId 수업 ID
     * @return 수업 ID로 필터링된 수강 목록
     * @throws SQLException SQL 예외
     */
    public List<AttendanceDTO> getEnrollmentsByClass(String classId) throws SQLException {
        String query = "SELECT * FROM DB2024_V_AttendingDetails WHERE classid = ?";
        return queryEnrollments(query, classId);
    }

    /**
     * 회원 이름으로 필터링하여 수강 목록을 조회합니다. (관리자용)
     * @param memberName 회원 이름
     * @return 회원 이름으로 필터링된 수강 목록
     * @throws SQLException SQL 예외
     */
    public List<AttendanceDTO> getEnrollmentsByMemberName(String memberName) throws SQLException {
        String query = "SELECT * FROM DB2024_V_AttendingDetails WHERE member_name LIKE ?";
        return queryEnrollments(query, "%" + memberName + "%");
    }

    /**
     * 수업과 회원 이름을 필터링하여 수강 목록을 조회합니다. (관리자용)
     * @param classId 수업 ID
     * @param memberName 회원 이름
     * @return 수업과 회원 이름으로 필터링된 수강 목록
     * @throws SQLException SQL 예외
     */
    public List<AttendanceDTO> filterEnrollmentsByClassAndMemberName(String classId, String memberName) throws SQLException {
        String query = "SELECT * FROM DB2024_V_AttendingDetails WHERE classid = ? AND member_name LIKE ?";
        return queryEnrollments(query, classId, "%" + memberName + "%");
    }
}
