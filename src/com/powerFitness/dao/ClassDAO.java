package com.powerFitness.dao;

import com.powerFitness.dto.ClassDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 수업 정보에 대한 데이터 액세스 객체입니다.
 */
public class ClassDAO {

    /**
     * 수업을 추가합니다.
     * @param classDTO 추가할 수업 정보
     */
    public void addClass(ClassDTO classDTO) {
        String query = "INSERT INTO DB2024_Classes (classid, centerid, instructorid, ctypeid, placeid, ctimeid, class_name, class_fee, description, max_people, current_people) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, classDTO.getClassId());
            pstmt.setString(2, classDTO.getCenterId());
            pstmt.setString(3, classDTO.getInstructorId());
            pstmt.setString(4, classDTO.getCtypeId());
            pstmt.setString(5, classDTO.getPlaceId());
            pstmt.setString(6, classDTO.getCtimeId());
            pstmt.setString(7, classDTO.getClassName());
            pstmt.setInt(8, classDTO.getClassFee());
            pstmt.setString(9, classDTO.getDescription());
            pstmt.setInt(10, classDTO.getMaxPeople());
            pstmt.setInt(11, classDTO.getCurrentPeople());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수업을 수정합니다.
     * @param classDTO 수정할 수업 정보
     */
    public void updateClass(ClassDTO classDTO) {
        String query = "UPDATE DB2024_Classes SET centerid = ?, instructorid = ?, ctypeid = ?, placeid = ?, ctimeid = ?, class_name = ?, class_fee = ?, description = ?, max_people = ?, current_people = ? WHERE classid = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, classDTO.getCenterId());
            pstmt.setString(2, classDTO.getInstructorId());
            pstmt.setString(3, classDTO.getCtypeId());
            pstmt.setString(4, classDTO.getPlaceId());
            pstmt.setString(5, classDTO.getCtimeId());
            pstmt.setString(6, classDTO.getClassName());
            pstmt.setInt(7, classDTO.getClassFee());
            pstmt.setString(8, classDTO.getDescription());
            pstmt.setInt(9, classDTO.getMaxPeople());
            pstmt.setInt(10, classDTO.getCurrentPeople());
            pstmt.setString(11, classDTO.getClassId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수업을 삭제합니다.
     * @param classId 삭제할 수업의 ID
     */
    public void deleteClass(String classId) {
        String query = "DELETE FROM DB2024_Classes WHERE classid = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, classId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 모든 수업 목록을 조회합니다.
     * @return 수업 목록
     */
    public List<ClassDTO> getAllClasses() {
        List<ClassDTO> classes = new ArrayList<>();
        String query = "SELECT * FROM DB2024_Classes";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ClassDTO classDTO = new ClassDTO();
                classDTO.setClassId(rs.getString("classid"));
                classDTO.setCenterId(rs.getString("centerid"));
                classDTO.setInstructorId(rs.getString("instructorid"));
                classDTO.setCtypeId(rs.getString("ctypeid"));
                classDTO.setPlaceId(rs.getString("placeid"));
                classDTO.setCtimeId(rs.getString("ctimeid"));
                classDTO.setClassName(rs.getString("class_name"));
                classDTO.setClassFee(rs.getInt("class_fee"));
                classDTO.setDescription(rs.getString("description"));
                classDTO.setMaxPeople(rs.getInt("max_people"));
                classDTO.setCurrentPeople(rs.getInt("current_people"));
                classes.add(classDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    // 센터 이름을 센터 아이디로 변환하는 메서드
    // (센터 이름으로 데이터베이스에서 찾아서 사용해야 하는데, 여기에선 더미 데이터로 대체하였습니다.)
    private String getCenterIdByName(String centerName) {
        switch (centerName) {
            case "서대문점":
                return "center01";
            case "강남점":
                return "center02";
            default:
                return null;
        }
    }

    /**
     * 수업을 검색합니다.
     * @param className 수업 이름
     * @param instructorName 강사 이름
     * @param sports 스포츠 종목
     * @param days 요일
     * @param centers 센터 이름
     * @return 검색된 수업 목록
     */
    public List<ClassDTO> searchClasses(String className, String instructorName, String sports, String days, String centers) {
        List<ClassDTO> classes = new ArrayList<>();
        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM DB2024_ClassDetails WHERE 1=1");

        if (className != null && !className.isEmpty()) {
            queryBuilder.append(" AND class_name LIKE ?");
        }
        if (instructorName != null && !instructorName.isEmpty()) {
            queryBuilder.append(" AND instructor_name LIKE ?");
        }
        if (sports != null && !sports.isEmpty()) {
            String[] sportArray = sports.split(",");
            queryBuilder.append(" AND sport IN (");
            for (int i = 0; i < sportArray.length; i++) {
                queryBuilder.append("?");
                if (i < sportArray.length - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");
        }
        if (days != null && !days.isEmpty()) {
            String[] dayArray = days.split(",");
            queryBuilder.append(" AND day IN (");
            for (int i = 0; i < dayArray.length; i++) {
                queryBuilder.append("?");
                if (i < dayArray.length - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");
        }
        if (centers != null && !centers.isEmpty()) {
            String[] centerArray = centers.split(",");
            queryBuilder.append(" AND centerid IN (");
            for (int i = 0; i < centerArray.length; i++) {
                String centerId = getCenterIdByName(centerArray[i]);
                if (centerId != null) {
                    queryBuilder.append("?");
                    if (i < centerArray.length - 1) {
                        queryBuilder.append(",");
                    }
                }
            }
            queryBuilder.append(")");
        }

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(queryBuilder.toString())) {

            int paramIndex = 1;
            if (className != null && !className.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + className + "%");
            }
            if (instructorName != null && !instructorName.isEmpty()) {
                pstmt.setString(paramIndex++, "%" + instructorName + "%");
            }
            if (sports != null && !sports.isEmpty()) {
                for (String sport : sports.split(",")) {
                    pstmt.setString(paramIndex++, sport);
                }
            }
            if (days != null && !days.isEmpty()) {
                for (String day : days.split(",")) {
                    pstmt.setString(paramIndex++, day);
                }
            }
            if (centers != null && !centers.isEmpty()) {
                for (String center : centers.split(",")) {
                    String centerId = getCenterIdByName(center);
                    if (centerId != null) {
                        pstmt.setString(paramIndex++, centerId);
                    }
                }
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ClassDTO classDTO = new ClassDTO();
                    classDTO.setClassId(rs.getString("classid"));
                    classDTO.setCenterId(rs.getString("centerid"));
                    classDTO.setInstructorId(rs.getString("instructorid"));
                    classDTO.setCtypeId(rs.getString("ctypeid"));
                    classDTO.setPlaceId(rs.getString("placeid"));
                    classDTO.setCtimeId(rs.getString("ctimeid"));
                    classDTO.setClassName(rs.getString("class_name"));
                    classDTO.setClassFee(rs.getInt("class_fee"));
                    classDTO.setDescription(rs.getString("description"));
                    classDTO.setMaxPeople(rs.getInt("max_people"));
                    classDTO.setCurrentPeople(rs.getInt("current_people"));
                    classes.add(classDTO);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * 수업의 상세 정보를 조회합니다.
     * @param classId 조회할 수업의 ID
     * @return 수업의 상세 정보
     */
    public ClassDTO getClassDetails(String classId) {
        ClassDTO classDTO = null;
        String query = "SELECT * FROM DB2024_ClassDetails WHERE classid = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, classId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    classDTO = new ClassDTO();
                    classDTO.setClassId(rs.getString("classid"));
                    classDTO.setCenterId(rs.getString("centerid"));
                    classDTO.setInstructorId(rs.getString("instructorid"));
                    classDTO.setCtypeId(rs.getString("ctypeid"));
                    classDTO.setPlaceId(rs.getString("placeid"));
                    classDTO.setCtimeId(rs.getString("ctimeid"));
                    classDTO.setClassName(rs.getString("class_name"));
                    classDTO.setClassFee(rs.getInt("class_fee"));
                    classDTO.setDescription(rs.getString("description"));
                    classDTO.setMaxPeople(rs.getInt("max_people"));
                    classDTO.setCurrentPeople(rs.getInt("current_people"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return classDTO;
    }
}

