package com.powerFitness.controller;

import com.powerFitness.dao.ClassDAO;
import com.powerFitness.dto.ClassDTO;

import java.util.List;

public class ClassController {
    private ClassDAO classDAO;

    public ClassController() {
        this.classDAO = new ClassDAO();
    }

    /**
     * 전체 수업 목록 조회
     * @return 전체 수업 리스트
     */
    public List<ClassDTO> getAllClasses() {
        return classDAO.getAllClasses();
    }

    /**
     * 수업 상세 정보 조회
     * @param classId 조회할 수업 ID
     * @return 수업 상세 정보
     */
    public ClassDTO getClassDetails(String classId) {
        return classDAO.getClassDetails(classId);
    }

    /**
     * 수업 추가
     * @param classDTO 추가할 수업 정보 DTO
     */
    public void addClass(ClassDTO classDTO) {
        classDAO.addClass(classDTO);
    }

    /**
     * 수업 수정
     * @param classDTO 수정할 수업 정보 DTO
     */
    public void updateClass(ClassDTO classDTO) {
        classDAO.updateClass(classDTO);
    }

    /**
     * 수업 삭제
     * @param classId 삭제할 수업 ID
     */
    public void deleteClass(String classId) {
        classDAO.deleteClass(classId);
    }

    /**
     * 수업 검색
     * @param className 검색할 수업 이름
     * @param instructorName 검색할 강사 이름
     * @param sports 검색할 스포츠 종류
     * @param days 검색할 요일
     * @param centers 검색할 센터
     * @return 검색된 수업 리스트
     */
    public List<ClassDTO> searchClasses(String className, String instructorName, String sports, String days, String centers) {
        return classDAO.searchClasses(className, instructorName, sports, days, centers);
    }
}
