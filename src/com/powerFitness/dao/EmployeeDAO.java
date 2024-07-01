package com.powerFitness.dao;

import com.powerFitness.dto.EmployeeDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    /**
     * 직원을 등록합니다.
     *
     * @param employee 등록할 직원의 정보를 담고 있는 EmployeeDTO 객체
     */
    public void addEmployee(EmployeeDTO employee) {
        String query = "INSERT INTO DB2024_Employees (employeeid, centerid, name, birthday, phone, gender, join_date, quit_date, address, password, title) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getEmployeeId());
            stmt.setString(2, employee.getCenterId());
            stmt.setString(3, employee.getName());
            stmt.setDate(4, new java.sql.Date(employee.getBirthday().getTime()));
            stmt.setString(5, employee.getPhone());
            stmt.setString(6, String.valueOf(employee.getGender()));
            stmt.setDate(7, new java.sql.Date(employee.getJoinDate().getTime()));
            stmt.setDate(8, (employee.getQuitDate() != null) ? new java.sql.Date(employee.getQuitDate().getTime()) : null);
            stmt.setString(9, employee.getAddress());
            stmt.setString(10, employee.getPassword());
            stmt.setString(11, employee.getTitle());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 특정 센터의 마지막 직원 아이디를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 마지막 직원 아이디
     */
    public String getLastEmployeeId(String centerId) {
        String query = "SELECT employeeid FROM DB2024_Employees WHERE centerid = ? ORDER BY employeeid DESC LIMIT 1";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, centerId);
            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getString("employeeid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 직원 정보를 수정합니다.
     *
     * @param employee 수정할 직원의 정보를 담고 있는 EmployeeDTO 객체
     */
    public void updateEmployee(EmployeeDTO employee) {
        String query = "UPDATE DB2024_Employees SET centerid = ?, name = ?, birthday = ?, phone = ?, gender = ?, join_date = ?, quit_date = ?, address = ?, password = ?, title = ? WHERE employeeid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employee.getCenterId());
            stmt.setString(2, employee.getName());
            stmt.setDate(3, new java.sql.Date(employee.getBirthday().getTime()));
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, String.valueOf(employee.getGender()));
            stmt.setDate(6, new java.sql.Date(employee.getJoinDate().getTime()));
            stmt.setDate(7, (employee.getQuitDate() != null) ? new java.sql.Date(employee.getQuitDate().getTime()) : null);
            stmt.setString(8, employee.getAddress());
            stmt.setString(9, employee.getPassword());
            stmt.setString(10, employee.getTitle());
            stmt.setString(11, employee.getEmployeeId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 직원을 삭제합니다.
     *
     * @param employeeId 삭제할 직원의 아이디
     */
    public void deleteEmployee(String employeeId) {
        String query = "DELETE FROM DB2024_Employees WHERE employeeid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 직원 정보를 조회합니다.
     *
     * @param employeeId 조회할 직원의 아이디 (모든 직원 조회 시에는 null 또는 빈 문자열)
     * @return 조회된 직원 정보를 담은 리스트
     */
    public List<EmployeeDTO> getEmployeeDetails(String employeeId) {
        String query;
        if (employeeId == null || employeeId.isEmpty()) {
            query = "SELECT * FROM DB2024_Employees"; // 전체 직원 조회
        } else {
            query = "SELECT * FROM DB2024_Employees WHERE employeeid = ?"; // 특정 직원 조회
        }
        List<EmployeeDTO> employees = new ArrayList<>();

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (employeeId != null && !employeeId.isEmpty()) {
                stmt.setString(1, employeeId);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EmployeeDTO employee = new EmployeeDTO();
                employee.setEmployeeId(rs.getString("employeeid"));
                employee.setCenterId(rs.getString("centerid"));
                employee.setName(rs.getString("name"));
                employee.setBirthday(rs.getDate("birthday"));
                employee.setPhone(rs.getString("phone"));
                employee.setGender(rs.getString("gender").charAt(0));
                employee.setJoinDate(rs.getDate("join_date"));
                employee.setQuitDate(rs.getDate("quit_date"));
                employee.setAddress(rs.getString("address"));
                employee.setPassword(rs.getString("password"));
                employee.setTitle(rs.getString("title"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
