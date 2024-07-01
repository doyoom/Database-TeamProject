package com.powerFitness.dao;

import com.powerFitness.dto.UserDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    /**
     * 공통 로직으로서 전화번호와 비밀번호를 이용하여 사용자를 조회합니다.
     *
     * @param phone    전화번호
     * @param password 비밀번호
     * @param query    실행할 쿼리
     * @param role     사용자 역할
     * @return 조회된 사용자 정보
     */
    private UserDTO getUserByPhoneAndPassword(String phone, String password, String query, String role) {
        UserDTO user = null;
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, phone);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new UserDTO();
                user.setUserId(rs.getString("userid"));
                user.setCenterId(rs.getString("centerid"));
                user.setName(rs.getString("name"));
                user.setRole(role); // 역할 설정
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 회원의 전화번호와 비밀번호를 이용하여 회원을 조회합니다.
     *
     * @param phone    회원의 전화번호
     * @param password 회원의 비밀번호
     * @return 조회된 회원 정보
     */
    public UserDTO getMemberByPhoneAndPassword(String phone, String password) {
        String query = "SELECT memberid AS userid, centerid, name FROM DB2024_Members WHERE phone = ? AND password = ?";
        return getUserByPhoneAndPassword(phone, password, query, "member");
    }

    /**
     * 직원의 전화번호와 비밀번호를 이용하여 직원을 조회합니다.
     *
     * @param phone    직원의 전화번호
     * @param password 직원의 비밀번호
     * @return 조회된 직원 정보
     */
    public UserDTO getEmployeeByPhoneAndPassword(String phone, String password) {
        String query = "SELECT employeeid AS userid, centerid, name FROM DB2024_Employees WHERE phone = ? AND password = ?";
        return getUserByPhoneAndPassword(phone, password, query, "employee");
    }
}
