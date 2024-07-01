package com.powerFitness.controller;

import com.powerFitness.service.PaymentService;
import com.powerFitness.dto.PaymentDTO;
import com.powerFitness.dto.UserDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * 결제 정보를 관리하는 컨트롤러 클래스입니다.
 */
public class PaymentController {
    private PaymentService paymentService;
    private UserDTO currentUser;

    /**
     * 기본 생성자
     * @param user 현재 로그인한 사용자
     */
    public PaymentController(UserDTO user) {
        this.paymentService = new PaymentService();
        this.currentUser = user;
    }
    
    /**
     * 현재 사용자 정보를 반환합니다.
     * @return 현재 사용자 정보
     */
    public UserDTO getCurrentUser() {
        return this.currentUser;
    }
    
    /**
     * 회원의 결제 내역을 조회합니다.
     * @return 회원의 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> fetchMemberPayments() throws SQLException {
        checkRole("member");
        return paymentService.getPaymentsByMember(currentUser.getUserId());
    }
    
    /**
     * 회원 이름으로 결제 내역을 조회합니다. (관리자용)
     * @param memberName 회원 이름
     * @return 회원의 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> fetchPaymentsByMemberName(String memberName) throws SQLException {
        checkRole("employee");
        return paymentService.getPaymentsByMemberName(memberName);
    }

    /**
     * 결제 상태별로 결제 내역을 조회합니다. (관리자용)
     * @param status 결제 상태
     * @return 결제 상태별 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> fetchPaymentsByStatus(String status) throws SQLException {
        checkRole("employee");
        return paymentService.getPaymentsByStatus(status);
    }

    /**
     * 전체 결제 내역을 조회합니다. (관리자용)
     * @return 전체 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> fetchAllPayments() throws SQLException {
        checkRole("employee");
        return paymentService.getAllPayments();
    }

    /**
     * 결제 상태와 회원 이름으로 결제 내역을 조회합니다. (관리자용)
     * @param status 결제 상태
     * @param memberName 회원 이름
     * @return 결제 상태와 회원 이름으로 필터링된 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> fetchFilteredPaymentsByStatusAndName(String status, String memberName) throws SQLException {
        checkRole("employee");
        return paymentService.filterPaymentsByStatusAndName(status, memberName);
    }
    
    /**
     * 권한을 확인합니다.
     * @param requiredRole 필요한 권한
     * @throws SecurityException 보안 예외
     */
    private void checkRole(String requiredRole) throws SecurityException {
        if (!currentUser.getRole().equals(requiredRole)) {
            throw new SecurityException("Access denied. Required role: " + requiredRole);
        }
    }

}
