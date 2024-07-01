package com.powerFitness.dao;

import com.powerFitness.dto.InstructorDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstructorDAO {

    /**
     * 강사를 등록합니다.
     *
     * @param instructor 등록할 강사 정보
     */
    public void addInstructor(InstructorDTO instructor) {
        String query = "INSERT INTO DB2024_Instructors (instructorid, centerid, name, birthday, phone, gender, join_date, quit_date, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, instructor.getInstructorId());
            stmt.setString(2, instructor.getCenterId());
            stmt.setString(3, instructor.getName());
            stmt.setDate(4, new java.sql.Date(instructor.getBirthday().getTime()));
            stmt.setString(5, instructor.getPhone());
            stmt.setString(6, String.valueOf(instructor.getGender()));
            stmt.setDate(7, new java.sql.Date(instructor.getJoinDate().getTime()));
            stmt.setDate(8, (instructor.getQuitDate() != null) ? new java.sql.Date(instructor.getQuitDate().getTime()) : null);
            stmt.setString(9, instructor.getAddress());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * centerId에 해당하는 마지막 강사 아이디를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 마지막 강사 아이디
     */
    public String getLastInstructorId(String centerId) {
        String query = "SELECT instructorid FROM DB2024_Instructors WHERE centerid = ? ORDER BY instructorid DESC LIMIT 1";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, centerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("instructorid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 강사 정보를 수정합니다.
     *
     * @param instructor 수정할 강사 정보
     */
    public void updateInstructor(InstructorDTO instructor) {
        String query = "UPDATE DB2024_Instructors SET centerid = ?, name = ?, birthday = ?, phone = ?, gender = ?, join_date = ?, quit_date = ?, address = ? WHERE instructorid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, instructor.getCenterId());
            stmt.setString(2, instructor.getName());
            stmt.setDate(3, new java.sql.Date(instructor.getBirthday().getTime()));
            stmt.setString(4, instructor.getPhone());
            stmt.setString(5, String.valueOf(instructor.getGender()));
            stmt.setDate(6, new java.sql.Date(instructor.getJoinDate().getTime()));
            stmt.setDate(7, (instructor.getQuitDate() != null) ? new java.sql.Date(instructor.getQuitDate().getTime()) : null);
            stmt.setString(8, instructor.getAddress());
            stmt.setString(9, instructor.getInstructorId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 강사를 삭제합니다.
     *
     * @param instructorId 삭제할 강사 아이디
     */
    public void deleteInstructor(String instructorId) {
        String query = "DELETE FROM DB2024_Instructors WHERE instructorid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, instructorId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 강사 정보를 조회합니다.
     *
     * @param instructorId 조회할 강사 아이디 (전체 조회일 경우 null)
     * @return 조회된 강사 정보 목록
     */
    public List<InstructorDTO> getInstructorDetails(String instructorId) {
        String query;
        if (instructorId == null || instructorId.isEmpty()) {
            query = "SELECT * FROM DB2024_Instructors"; // 전체 강사 조회
        } else {
            query = "SELECT * FROM DB2024_Instructors WHERE instructorid = ?"; // 특정 강사 조회
        }

        List<InstructorDTO> instructors = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (instructorId != null && !instructorId.isEmpty()) {
                stmt.setString(1, instructorId);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                InstructorDTO instructor = new InstructorDTO();
                instructor.setInstructorId(rs.getString("instructorid"));
                instructor.setCenterId(rs.getString("centerid"));
                instructor.setName(rs.getString("name"));
                instructor.setBirthday(rs.getDate("birthday"));
                instructor.setPhone(rs.getString("phone"));
                instructor.setGender(rs.getString("gender").charAt(0));
                instructor.setJoinDate(rs.getDate("join_date"));
                instructor.setQuitDate(rs.getDate("quit_date"));
                instructor.setAddress(rs.getString("address"));
                instructors.add(instructor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructors;
    }
}
