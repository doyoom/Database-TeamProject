package com.powerFitness.service;

import com.powerFitness.dao.EnrollmentDAO;
import com.powerFitness.dto.AttendanceDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class EnrollmentService {
    private EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    /**
     * 수강 가능한 수업 목록을 조회합니다.
     *
     * @param targetMonth 조회할 대상 월(YYYY-MM 형식)
     * @return 수강 가능한 수업 목록
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public List<AttendanceDTO> getAvailableClasses(String targetMonth) throws SQLException {
        return enrollmentDAO.getAvailableClasses(targetMonth);
    }

    /**
     * 특정 수업의 수강 가능 여부를 확인합니다.
     *
     * @param classId     수업 ID
     * @param targetMonth 조회할 대상 월(YYYY-MM 형식)
     * @return 수강 가능 여부
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public boolean isClassAvailable(String classId, String targetMonth) throws SQLException {
        return enrollmentDAO.isClassAvailable(classId, targetMonth);
    }

    /**
     * 수강을 신청합니다.
     *
     * @param memberId    회원 ID
     * @param classId     수업 ID
     * @param targetMonth 신청 대상 월(YYYY-MM 형식)
     * @return 수강 신청 결과
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public boolean enrollInClass(String memberId, String classId, String targetMonth) throws SQLException {
        return enrollmentDAO.enrollInClass(memberId, classId, targetMonth);
    }

    /**
     * 결제 미완료 상태에서 수강 신청을 취소합니다.
     *
     * @param attendId 수강 ID
     * @param payId    결제 ID
     * @return 취소 결과
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public boolean cancelEnrollmentWithoutPayment(String attendId, String payId) throws SQLException {
        return enrollmentDAO.cancelEnrollmentWithoutPayment(attendId, payId);
    }

    /**
     * 결제 완료 상태에서 수강 신청을 취소합니다.
     *
     * @param attendId 수강 ID
     * @param payId    결제 ID
     * @return 취소 결과
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public boolean cancelEnrollmentWithPayment(String attendId, String payId) throws SQLException {
        return enrollmentDAO.cancelEnrollmentWithPayment(attendId, payId);
    }

    /**
     * 결제를 승인하고 결제 상태를 업데이트합니다.
     *
     * @param payId    결제 ID
     * @param payType  결제 유형
     * @param payDate  결제 일자
     * @return 결제 승인 및 업데이트 결과
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public boolean approvePayment(String payId, String payType, Date payDate) throws SQLException {
        return enrollmentDAO.approvePayment(payId, payType, payDate);
    }

    /**
     * 결제를 취소하고 결제 상태를 업데이트합니다.
     *
     * @param payId 결제 ID
     * @return 결제 취소 및 업데이트 결과
     * @throws SQLException SQL 예외가 발생할 경우
     */
    public boolean cancelPayment(String payId) throws SQLException {
        return enrollmentDAO.cancelPayment(payId);
    }
}
