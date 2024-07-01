package com.powerFitness.controller;

import com.powerFitness.dto.UserDTO;
import com.powerFitness.service.UserService;

/**
 * 로그인과 관련된 작업을 담당하는 컨트롤러 클래스입니다.
 */
public class LoginController {
    private UserService userService;

    /**
     * 기본 생성자
     */
    public LoginController() {
        this.userService = new UserService();
    }

    /**
     * 사용자 로그인을 처리합니다.
     * @param phone 사용자 전화번호
     * @param password 사용자 비밀번호
     * @param role 사용자 역할 (회원 또는 직원)
     * @return 로그인한 사용자 정보
     */
    public UserDTO login(String phone, String password, String role) {
        UserDTO user = null;
        if ("member".equals(role)) {
            user = userService.authenticateMember(phone, password);
        } else if ("employee".equals(role)) {
            user = userService.authenticateEmployee(phone, password);
        }
        return user;
    }
}
