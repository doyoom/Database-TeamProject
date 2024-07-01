package com.powerFitness.service;

import com.powerFitness.dao.ClassDAO;
import com.powerFitness.dto.ClassDTO;
import com.powerFitness.utils.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ClassService {
    private ClassDAO classDAO;

    public ClassService() {
        this.classDAO = new ClassDAO();
    }

    /**
     * 수업을 추가합니다.
     *
     * @param classDTO 추가할 수업 정보
     */
    public void addClass(ClassDTO classDTO) {
        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false);

            try {
                classDAO.addClass(classDTO);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Failed to add class", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수업을 수정합니다.
     *
     * @param classDTO 수정할 수업 정보
     */
    public void updateClass(ClassDTO classDTO) {
        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false);

            try {
                classDAO.updateClass(classDTO);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Failed to update class", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 수업을 삭제합니다.
     *
     * @param classId 삭제할 수업의 ID
     */
    public void deleteClass(String classId) {
        try (Connection conn = DBUtils.getConnection()) {
            conn.setAutoCommit(false);

            try {
                classDAO.deleteClass(classId);
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Failed to delete class", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 전체 수업을 조회합니다.
     *
     * @return 전체 수업 목록
     */
    public List<ClassDTO> getAllClasses() {
        try (Connection conn = DBUtils.getConnection()) {
            return classDAO.getAllClasses();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 수업을 검색합니다.
     *
     * @param className    수업 이름
     * @param instructorName 강사 이름
     * @param sport        운동 종목
     * @param day          요일
     * @param centerName   센터 이름
     * @return 검색된 수업 목록
     */
    public List<ClassDTO> searchClasses(String className, String instructorName, String sport, String day, String centerName) {
        try (Connection conn = DBUtils.getConnection()) {
            return classDAO.searchClasses(className, instructorName, sport, day, centerName);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 수업의 상세 정보를 조회합니다.
     *
     * @param classId 수업 ID
     * @return 수업 상세 정보
     */
    public ClassDTO getClassDetails(String classId) {
        try (Connection conn = DBUtils.getConnection()) {
            return classDAO.getClassDetails(classId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
