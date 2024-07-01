package com.powerFitness.service;

import com.powerFitness.dao.UserDAO;
import com.powerFitness.dto.UserDTO;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    /**
     * 회원 로그인을 인증합니다.
     *
     * @param phone    전화번호
     * @param password 비밀번호
     * @return 인증된 회원 정보
     */
    public UserDTO authenticateMember(String phone, String password) {
        return userDAO.getMemberByPhoneAndPassword(phone, password);
    }

    /**
     * 직원 로그인을 인증합니다.
     *
     * @param phone    전화번호
     * @param password 비밀번호
     * @return 인증된 직원 정보
     */
    public UserDTO authenticateEmployee(String phone, String password) {
        return userDAO.getEmployeeByPhoneAndPassword(phone, password);
    }
}
