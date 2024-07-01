package com.powerFitness.dao;

import com.powerFitness.dto.PaymentDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    private void setParameters(PreparedStatement pstmt, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
    }

    /**
     * 공통 메소드로서 쿼리를 실행하고 결과를 반환합니다.
     *
     * @param query  실행할 쿼리
     * @param params 쿼리의 매개변수
     * @return 결과 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    private List<PaymentDTO> queryPayments(String query, Object... params) throws SQLException {
        List<PaymentDTO> payments = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            setParameters(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                PaymentDTO payment = new PaymentDTO();
                payment.setMemberId(rs.getString("memberid"));
                payment.setMemberName(rs.getString("member_name"));
                payment.setPayId(rs.getString("payid"));
                payment.setPayType(rs.getString("pay_type"));
                payment.setPayFee(rs.getInt("pay_fee"));
                payment.setDate(rs.getString("date"));
                payment.setStatus(rs.getString("status"));
                payments.add(payment);
            }
        }
        return payments;
    }

    /**
     * 회원별 결제 내역을 조회합니다.
     *
     * @param memberId 회원 아이디
     * @return 해당 회원의 결제 내역 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    public List<PaymentDTO> getPaymentsByMember(String memberId) throws SQLException {
        String query = "SELECT * FROM DB2024_V_Payment WHERE memberid = ?";
        return queryPayments(query, memberId);
    }

    /**
     * 전체 결제 내역을 조회합니다.
     *
     * @return 전체 결제 내역 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    public List<PaymentDTO> getAllPayments() throws SQLException {
        String query = "SELECT * FROM DB2024_V_Payment";
        return queryPayments(query);
    }

    /**
     * 결제 상태별 결제 내역을 조회합니다.
     *
     * @param status 결제 상태
     * @return 해당 결제 상태의 결제 내역 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    public List<PaymentDTO> getPaymentsByStatus(String status) throws SQLException {
        String query = "SELECT * FROM DB2024_V_Payment WHERE status = ?";
        return queryPayments(query, status);
    }

    /**
     * 회원 이름으로 결제 내역을 조회합니다.
     *
     * @param memberName 회원 이름 (일부 또는 전체)
     * @return 해당 회원 이름을 포함하는 결제 내역 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    public List<PaymentDTO> getPaymentsByMemberName(String memberName) throws SQLException {
        String query = "SELECT * FROM DB2024_V_Payment WHERE member_name LIKE ?";
        return queryPayments(query, "%" + memberName + "%");
    }

    /**
     * 결제 상태와 회원 이름으로 필터링된 결제 내역을 조회합니다.
     *
     * @param status     결제 상태
     * @param memberName 회원 이름 (일부 또는 전체)
     * @return 해당 결제 상태와 회원 이름을 포함하는 결제 내역 리스트
     * @throws SQLException SQL 예외 발생 시
     */
    public List<PaymentDTO> filterPaymentsByStatusAndName(String status, String memberName) throws SQLException {
        String query = "SELECT * FROM (" +
                "SELECT * FROM DB2024_V_Payment WHERE status = ?" +
                ") AS FilteredByStatus " +
                "WHERE member_name LIKE ?";
        return queryPayments(query, status, "%" + memberName + "%");
    }

}
