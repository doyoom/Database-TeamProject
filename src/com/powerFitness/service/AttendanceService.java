package com.powerFitness.service;

import com.powerFitness.dao.AttendanceDAO;
import com.powerFitness.dto.AttendanceDTO;

import java.sql.SQLException;
import java.util.List;

public class AttendanceService {
    private AttendanceDAO attendanceDAO;

    public AttendanceService() {
        this.attendanceDAO = new AttendanceDAO();
    }

    /**
     * 회원별 수강 목록을 조회합니다. (회원용)
     *
     * @param memberId 회원 아이디
     * @return 수강 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<AttendanceDTO> getAttendingByMember(String memberId) throws SQLException {
        return attendanceDAO.getAttendingByMember(memberId);
    }

    /**
     * 전체 회원의 수강 목록을 조회합니다. (관리자용)
     *
     * @return 수강 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<AttendanceDTO> getAllAttending() throws SQLException {
        return attendanceDAO.getAllAttending();
    }

    /**
     * 특정 수업 ID로 수강 목록을 조회합니다. (관리자용)
     *
     * @param classId 수업 아이디
     * @return 수강 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<AttendanceDTO> getEnrollmentsByClass(String classId) throws SQLException {
        return attendanceDAO.getEnrollmentsByClass(classId);
    }

    /**
     * 회원 이름으로 수강 목록을 필터링하여 조회합니다. (관리자용)
     *
     * @param memberName 회원 이름
     * @return 수강 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<AttendanceDTO> getEnrollmentsByMemberName(String memberName) throws SQLException {
        return attendanceDAO.getEnrollmentsByMemberName(memberName);
    }

    /**
     * 수업 ID와 회원 이름으로 수강 목록을 필터링하여 조회합니다. (관리자용)
     *
     * @param classId    수업 아이디
     * @param memberName 회원 이름
     * @return 수강 목록
     * @throws SQLException SQL 예외 발생 시
     */
    public List<AttendanceDTO> filterEnrollmentsByClassAndMemberName(String classId, String memberName) throws SQLException {
        return attendanceDAO.filterEnrollmentsByClassAndMemberName(classId, memberName);
    }
}
