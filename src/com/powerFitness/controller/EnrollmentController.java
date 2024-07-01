package com.powerFitness.controller;

import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.dto.UserDTO;
import com.powerFitness.service.EnrollmentService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * 수강 등록과 관련된 작업을 담당하는 컨트롤러 클래스입니다.
 */
public class EnrollmentController {
    private EnrollmentService enrollmentService = new EnrollmentService();
    private UserDTO loggedInUser;
   
    /**
     * 생성자
     * @param loggedInUser 현재 로그인한 사용자 정보
     */
    public EnrollmentController(UserDTO loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    /**
     * 수강 가능한 수업 목록을 가져옵니다.
     * @param targetMonth 조회할 대상 월
     * @return 수강 가능한 수업 목록
     */
    public List<AttendanceDTO> getAvailableClasses(String targetMonth) {
        try {
            return enrollmentService.getAvailableClasses(targetMonth);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 특정 수업의 수강 가능 여부를 확인합니다.
     * @param classId 수업 ID
     * @param targetMonth 조회할 대상 월
     * @return 수강 가능 여부
     */
    public boolean isClassAvailable(String classId, String targetMonth) {
        try {
            return enrollmentService.isClassAvailable(classId, targetMonth);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
     * 권한을 확인합니다.
     * @param requiredRole 필요한 권한
     * @return 권한 확인 결과
     */
    private boolean hasRole(String requiredRole) {
        return requiredRole.equalsIgnoreCase(loggedInUser.getRole());
    }

    /**
     * 수강신청을 합니다.
     * @param user 수강 신청자 정보
     * @param classId 수업 ID
     * @param targetMonth 대상 월
     * @return 수강 신청 결과
     * @throws SQLException SQL 예외
     */
    public boolean enrollInClass(UserDTO user, String classId, String targetMonth) throws SQLException {
    	this.loggedInUser = user;
        if (hasRole("member")) {
            return enrollmentService.enrollInClass(loggedInUser.getUserId(), classId, targetMonth);
        } else {
            System.out.println("권한이 없습니다.");
            return false;
        }
    }

    /**
     * 결제 미완료 수강 취소를 합니다.
     * @param attendId 수강 ID
     * @param payId 결제 ID
     * @return 취소 결과
     * @throws SQLException SQL 예외
     */
    public boolean cancelEnrollmentWithoutPayment(String attendId, String payId) throws SQLException {
        if (hasRole("member")) {
            return enrollmentService.cancelEnrollmentWithoutPayment(attendId, payId);
        } else {
            System.out.println("권한이 없습니다.");
            return false;
        }
    }

    /**
     * 결제 완료 수강 취소를 합니다.
     * @param attendId 수강 ID
     * @param payId 결제 ID
     * @return 취소 결과
     * @throws SQLException SQL 예외
     */
    public boolean cancelEnrollmentWithPayment(String attendId, String payId) throws SQLException {
        if (hasRole("member")) {
            return enrollmentService.cancelEnrollmentWithPayment(attendId, payId);
        } else {
            System.out.println("권한이 없습니다.");
            return false;
        }
    }

    /**
     * 결제를 승인합니다.
     * @param payId 결제 ID
     * @param payType 결제 유형
     * @param payDate 결제 일자
     * @return 승인 결과
     * @throws SQLException SQL 예외
     */
    public boolean approvePayment(String payId, String payType, Date payDate) throws SQLException {
        if (hasRole("employee")) {
            return enrollmentService.approvePayment(payId, payType, payDate);
        } else {
            System.out.println("권한이 없습니다.");
            return false;
        }
    }

    /**
     * 결제를 취소합니다.
     * @param payId 결제 ID
     * @return 취소 결과
     * @throws SQLException SQL 예외
     */
    public boolean cancelPayment(String payId) throws SQLException {
        if (hasRole("employee")) {
            return enrollmentService.cancelPayment(payId);
        } else {
            System.out.println("권한이 없습니다.");
            return false;
        }
    }
}
