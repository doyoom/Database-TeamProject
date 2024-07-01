// InstructorController.java

package com.powerFitness.controller;

import com.powerFitness.dto.InstructorDTO;
import com.powerFitness.service.InstructorService;
import java.util.List;

/**
 * 강사 관련 작업을 담당하는 컨트롤러 클래스입니다.
 */
public class InstructorController {

    private InstructorService instructorService;

    /**
     * 기본 생성자
     */
    public InstructorController() {
        this.instructorService = new InstructorService();
    }

    /**
     * 강사를 추가합니다.
     * @param instructor 추가할 강사 정보
     */
    public void addInstructor(InstructorDTO instructor) {
        // InstructorService의 addInstructor 메서드 호출
        instructorService.addInstructor(instructor);
    }

    /**
     * 강사 정보를 수정합니다.
     * @param instructor 수정할 강사 정보
     */
    public void updateInstructor(InstructorDTO instructor) {
        instructorService.updateInstructor(instructor);
    }

    /**
     * 강사를 삭제합니다.
     * @param instructorId 삭제할 강사 ID
     */
    public void deleteInstructor(String instructorId) {
        instructorService.deleteInstructor(instructorId);
    }

    /**
     * 강사 정보를 조회합니다.
     * @param instructorId 조회할 강사 ID
     * @return 해당 강사의 정보 리스트
     */
    public List<InstructorDTO> getInstructorDetails(String instructorId) {
        return instructorService.getInstructorDetails(instructorId);
    }
    
    /**
     * 마지막으로 등록된 강사의 ID를 조회합니다.
     * @param centerId 센터 ID
     * @return 마지막으로 등록된 강사의 ID
     */
    public String getLastInstructorId(String centerId) {
        return instructorService.getLastInstructorId(centerId);
    }
}
