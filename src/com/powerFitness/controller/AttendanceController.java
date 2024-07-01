package com.powerFitness.controller;

import com.powerFitness.service.AttendanceService;
import com.powerFitness.dto.AttendanceDTO;
import com.powerFitness.dto.UserDTO;
import java.sql.SQLException;
import java.util.List;

public class AttendanceController {
    private AttendanceService attendanceService;
    private UserDTO currentUser;

    public AttendanceController(UserDTO user) {
        super(); 
        this.attendanceService = new AttendanceService();
        this.currentUser = user;
    }
    
    public UserDTO getCurrentUser() {
        return this.currentUser;
    }

    private void checkRole(String requiredRole) throws SecurityException {
        if (!currentUser.getRole().equals(requiredRole)) {
            throw new SecurityException("Access denied. Only " + requiredRole + " can access this information.");
        }
    }

    /**
     * 회원용 메소드 - 회원이 자신의 수강 목록 조회
     * @return 회원의 수강 목록
     * @throws SQLException 데이터베이스 오류 발생 시 예외
     * @throws SecurityException 권한 없는 사용자가 접근 시 예외
     */
    public List<AttendanceDTO> fetchMemberAttendances() throws SQLException {
        checkRole("member");
        return attendanceService.getAttendingByMember(currentUser.getUserId());
    }

    /**
     * 관리자용 메소드 - 전체 수강 내역 조회
     * @return 전체 수강 내역
     * @throws SQLException 데이터베이스 오류 발생 시 예외
     * @throws SecurityException 권한 없는 사용자가 접근 시 예외
     */
    public List<AttendanceDTO> fetchAllAttendances() throws SQLException {
        checkRole("employee");
        return attendanceService.getAllAttending();
    }

    /**
     * 관리자용 메소드 - 수업 ID로 수강 목록 조회
     * @param classId 조회할 수업 ID
     * @return 해당 수업의 수강 목록
     * @throws SQLException 데이터베이스 오류 발생 시 예외
     * @throws SecurityException 권한 없는 사용자가 접근 시 예외
     */
    public List<AttendanceDTO> fetchEnrollmentsByClass(String classId) throws SQLException {
        checkRole("employee");
        return attendanceService.getEnrollmentsByClass(classId);
    }

    /**
     * 관리자용 메소드 - 회원 이름으로 수강 목록 필터링 조회
     * @param memberName 조회할 회원 이름
     * @return 해당 회원의 수강 목록
     * @throws SQLException 데이터베이스 오류 발생 시 예외
     * @throws SecurityException 권한 없는 사용자가 접근 시 예외
     */
    public List<AttendanceDTO> fetchEnrollmentsByMemberName(String memberName) throws SQLException {
        checkRole("employee");
        return attendanceService.getEnrollmentsByMemberName(memberName);
    }

    /**
     * 관리자용 메소드 - 수업 ID와 회원 이름으로 수강 목록 필터링 조회
     * @param classId 조회할 수업 ID
     * @param memberName 조회할 회원 이름
     * @return 해당 수업과 회원 이름으로 필터링된 수강 목록
     * @throws SQLException 데이터베이스 오류 발생 시 예외
     * @throws SecurityException 권한 없는 사용자가 접근 시 예외
     */
    public List<AttendanceDTO> filterEnrollmentsByClassAndMemberName(String classId, String memberName) throws SQLException {
        checkRole("employee");
        return attendanceService.filterEnrollmentsByClassAndMemberName(classId, memberName);
    }
}
