package com.powerFitness.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.powerFitness.dto.MemberDTO;
import com.powerFitness.utils.DBUtils;

public class MemberDAO {

    /**
     * 회원을 등록합니다.
     *
     * @param member 등록할 회원 정보
     */
    public void registerMember(MemberDTO member) {
        String query = "INSERT INTO DB2024_Members (memberid, centerid, name, birthday, phone, gender, join_date, quit_date, address, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setString(1, member.getMemberId());
            pStmt.setString(2, member.getCenterId());
            pStmt.setString(3, member.getName());
            pStmt.setDate(4, member.getBirthday());
            pStmt.setString(5, member.getPhone());
            pStmt.setString(6, String.valueOf(member.getGender()));
            pStmt.setDate(7, member.getJoinDate());
            pStmt.setDate(8, member.getQuitDate());
            pStmt.setString(9, member.getAddress());
            pStmt.setString(10, member.getPassword());

            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원 아이디에 해당하는 회원 정보를 조회합니다.
     *
     * @param memberId 조회할 회원 아이디
     * @return 조회된 회원 정보
     */
    public MemberDTO getMemberById(String memberId) {
        String query = "SELECT * FROM DB2024_Members WHERE memberid = ?";
        MemberDTO member = null;
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setString(1, memberId);

            try (ResultSet resultSet = pStmt.executeQuery()) {
                if (resultSet.next()) {
                    member = new MemberDTO();
                    member.setMemberId(resultSet.getString("memberid"));
                    member.setCenterId(resultSet.getString("centerid"));
                    member.setName(resultSet.getString("name"));
                    member.setBirthday(resultSet.getDate("birthday"));
                    member.setPhone(resultSet.getString("phone"));
                    member.setGender(resultSet.getString("gender").charAt(0));
                    member.setJoinDate(resultSet.getDate("join_date"));
                    member.setQuitDate(resultSet.getDate("quit_date"));
                    member.setAddress(resultSet.getString("address"));
                    member.setPassword(resultSet.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return member;
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param member 수정할 회원 정보
     */
    public void updateMember(MemberDTO member) {
        String query = "UPDATE DB2024_Members SET centerid = ?, name = ?, birthday = ?, phone = ?, gender = ?, join_date = ?, quit_date = ?, address = ?, password = ? WHERE memberid = ?";
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setString(1, member.getCenterId());
            pStmt.setString(2, member.getName());
            pStmt.setDate(3, member.getBirthday());
            pStmt.setString(4, member.getPhone());
            pStmt.setString(5, String.valueOf(member.getGender()));
            pStmt.setDate(6, member.getJoinDate());
            pStmt.setDate(7, member.getQuitDate());
            pStmt.setString(8, member.getAddress());
            pStmt.setString(9, member.getPassword());
            pStmt.setString(10, member.getMemberId());

            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원의 비밀번호를 변경합니다.
     *
     * @param memberId    비밀번호를 변경할 회원 아이디
     * @param newPassword 새로운 비밀번호
     */
    public void changePassword(String memberId, String newPassword) {
        String query = "UPDATE DB2024_Members SET password = ? WHERE memberid = ?";
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setString(1, newPassword);
            pStmt.setString(2, memberId);

            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param memberId 삭제할 회원 아이디
     */
    public void deleteMember(String memberId) {
        String query = "DELETE FROM DB2024_Members WHERE memberid = ?";
        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            pStmt.setString(1, memberId);

            pStmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 키워드로 회원을 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @return 검색된 회원 목록
     */
    public List<MemberDTO> searchMembers(String keyword) {
        List<MemberDTO> members = new ArrayList<>();
        String query = "SELECT * FROM DB2024_Members WHERE name LIKE ? OR phone LIKE ?";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query)) {

            // name과 phone 둘 다 keyword를 포함하는지를 확인
            pStmt.setString(1, "%" + keyword + "%");
            pStmt.setString(2, "%" + keyword + "%");

            try (ResultSet resultSet = pStmt.executeQuery()) {
                while (resultSet.next()) {
                    MemberDTO member = new MemberDTO();
                    member.setMemberId(resultSet.getString("memberid"));
                    member.setCenterId(resultSet.getString("centerid"));
                    member.setName(resultSet.getString("name"));
                    member.setBirthday(resultSet.getDate("birthday"));
                    member.setPhone(resultSet.getString("phone"));
                    member.setGender(resultSet.getString("gender").charAt(0));
                    member.setJoinDate(resultSet.getDate("join_date"));
                    member.setQuitDate(resultSet.getDate("quit_date"));
                    member.setAddress(resultSet.getString("address"));
                    member.setPassword(resultSet.getString("password"));
                    members.add(member);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    /**
     * center별 회원의 가장 마지막 memberid를 검색합니다.
     *
     * @param centerId 검색할 센터 아이디
     * @return centerId에 해당하는 가장 마지막 memberid
     */
    public String getLastMemberId(String centerId) {
        String sql = "SELECT memberid FROM DB2024_Members WHERE centerid = ? ORDER BY memberid DESC LIMIT 1";
        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, centerId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("memberid");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 모든 회원의 정보를 조회합니다.
     *
     * @return 모든 회원의 정보 목록
     */
    public List<MemberDTO> getAllMembers() {
        List<MemberDTO> members = new ArrayList<>();
        String query = "SELECT * FROM DB2024_Members";

        try (Connection connection = DBUtils.getConnection();
             PreparedStatement pStmt = connection.prepareStatement(query);
             ResultSet resultSet = pStmt.executeQuery()) {

            while (resultSet.next()) {
                MemberDTO member = new MemberDTO();
                member.setMemberId(resultSet.getString("memberid"));
                member.setCenterId(resultSet.getString("centerid"));
                member.setName(resultSet.getString("name"));
                member.setBirthday(resultSet.getDate("birthday"));
                member.setPhone(resultSet.getString("phone"));
                member.setGender(resultSet.getString("gender").charAt(0));
                member.setJoinDate(resultSet.getDate("join_date"));
                member.setQuitDate(resultSet.getDate("quit_date"));
                member.setAddress(resultSet.getString("address"));
                member.setPassword(resultSet.getString("password"));
                members.add(member);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }
}
