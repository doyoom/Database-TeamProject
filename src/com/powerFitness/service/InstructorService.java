package com.powerFitness.service;

import com.powerFitness.dao.InstructorDAO;
import com.powerFitness.dto.InstructorDTO;

import java.util.List;

public class InstructorService {

    private InstructorDAO instructorDAO;

    public InstructorService() {
        this.instructorDAO = new InstructorDAO();
    }

    /**
     * 강사를 추가합니다.
     *
     * @param instructor 추가할 강사 정보
     */
    public void addInstructor(InstructorDTO instructor) {
        // InstructorDAO의 addInstructor 메서드에서 강사 아이디 생성 및 등록을 처리하므로 해당 메서드만 호출
        instructorDAO.addInstructor(instructor);
    }

    /**
     * 강사 정보를 수정합니다.
     *
     * @param instructor 수정할 강사 정보
     */
    public void updateInstructor(InstructorDTO instructor) {
        instructorDAO.updateInstructor(instructor);
    }

    /**
     * 강사를 삭제합니다.
     *
     * @param instructorId 삭제할 강사의 아이디
     */
    public void deleteInstructor(String instructorId) {
        instructorDAO.deleteInstructor(instructorId);
    }

    /**
     * 강사 정보를 조회합니다.
     *
     * @param instructorId 조회할 강사의 아이디
     * @return 조회된 강사 정보 목록
     */
    public List<InstructorDTO> getInstructorDetails(String instructorId) {
        return instructorDAO.getInstructorDetails(instructorId);
    }
    
    /**
     * 마지막 강사 아이디를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 마지막 강사 아이디
     */
    public String getLastInstructorId(String centerId) {
        return instructorDAO.getLastInstructorId(centerId);
    }
}
