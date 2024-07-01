package com.powerFitness.service;

import com.powerFitness.dao.PaymentDAO;
import com.powerFitness.dto.PaymentDTO;

import java.sql.SQLException;
import java.util.List;

public class PaymentService {
    private PaymentDAO paymentDAO;

    public PaymentService() {
        this.paymentDAO = new PaymentDAO();
    }
    
    /**
     * 회원별 결제 내역을 조회합니다.
     *
     * @param memberId 회원 아이디
     * @return 해당 회원의 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> getPaymentsByMember(String memberId) throws SQLException {
        return paymentDAO.getPaymentsByMember(memberId);
    }

    /**
     * 전체 결제 내역을 조회합니다.
     *
     * @return 전체 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> getAllPayments() throws SQLException {
        return paymentDAO.getAllPayments();
    }

    /**
     * 결제 상태별 결제 내역을 조회합니다.
     *
     * @param status 결제 상태
     * @return 해당 결제 상태의 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> getPaymentsByStatus(String status) throws SQLException {
        return paymentDAO.getPaymentsByStatus(status);
    }

    /**
     * 회원 이름별 결제 내역을 조회합니다.
     *
     * @param memberName 회원 이름
     * @return 해당 회원 이름의 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> getPaymentsByMemberName(String memberName) throws SQLException {
        return paymentDAO.getPaymentsByMemberName(memberName);
    }

    /**
     * 결제 상태와 회원 이름으로 필터링된 결제 내역을 조회합니다.
     *
     * @param status     결제 상태
     * @param memberName 회원 이름
     * @return 해당 결제 상태와 회원 이름으로 필터링된 결제 내역 목록
     * @throws SQLException SQL 예외
     */
    public List<PaymentDTO> filterPaymentsByStatusAndName(String status, String memberName) throws SQLException {
        return paymentDAO.filterPaymentsByStatusAndName(status, memberName);
    }
}
