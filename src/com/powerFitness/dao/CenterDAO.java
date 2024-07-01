package com.powerFitness.dao;

import com.powerFitness.dto.CenterDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 센터 정보에 대한 데이터 액세스 객체입니다.
 */
public class CenterDAO {

    /**
     * 센터 정보를 조회합니다.
     * @param centerId 센터 ID
     * @return 센터 정보 목록
     */
    public List<CenterDTO> getCenterDetails(String centerId) {
        String query;
        if (centerId == null || centerId.isEmpty()) {
            query = "SELECT * FROM DB2024_Centers"; // 전체 센터 조회
        } else {
            query = "SELECT * FROM DB2024_Centers WHERE centerid = ?"; // 특정 센터 조회
        }

        List<CenterDTO> centers = new ArrayList<>();
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            if (centerId != null && !centerId.isEmpty()) {
                stmt.setString(1, centerId);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CenterDTO center = new CenterDTO();
                center.setCenterId(rs.getString("centerid"));
                center.setName(rs.getString("name"));
                center.setAddress(rs.getString("address"));
                center.setPhone(rs.getString("phone"));
                center.setBusinessHour(rs.getString("business_hour"));
                center.setDescription(rs.getString("description"));
                centers.add(center);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return centers;
    }

    /**
     * 센터 정보를 수정합니다.
     * @param center 수정할 센터 정보
     */
    public void updateCenter(CenterDTO center) {
        String query = "UPDATE DB2024_Centers SET name = ?, address = ?, phone = ?, business_hour = ?, description = ? WHERE centerid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, center.getName());
            stmt.setString(2, center.getAddress());
            stmt.setString(3, center.getPhone());
            stmt.setString(4, center.getBusinessHour());
            stmt.setString(5, center.getDescription());
            stmt.setString(6, center.getCenterId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 센터 내 등록 회원 수를 조회합니다.
     * @param centerId 센터 ID
     * @return 등록 회원 수
     */
    public int getMemberCount(String centerId) {
        String query = "SELECT COUNT(*) AS member_count FROM DB2024_Members WHERE centerid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, centerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("member_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 센터별 수업 수를 조회합니다.
     * @param centerId 센터 ID
     * @return 수업 수
     */
    public int getClassCount(String centerId) {
        String query = "SELECT COUNT(*) AS class_count FROM DB2024_Classes WHERE centerid = ?";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, centerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("class_count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
