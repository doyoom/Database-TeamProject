package com.powerFitness.controller;

import com.powerFitness.dto.CenterDTO;
import com.powerFitness.service.CenterService;

import java.util.List;

public class CenterController {
    private CenterService centerService;

    public CenterController() {
        this.centerService = new CenterService();
    }

    /**
     * 센터 정보 조회
     * @param centerId 조회할 센터 ID. null인 경우 모든 센터 정보를 조회.
     * @return 센터 정보 리스트
     */
    public List<CenterDTO> getCenterDetails(String centerId) {
        return centerService.getCenterDetails(centerId);
    }

    /**
     * 센터 정보 수정
     * @param center 수정할 센터 정보 DTO
     */
    public void updateCenter(CenterDTO center) {
        centerService.updateCenter(center);
    }

    /**
     * 센터 내 등록 회원수 조회
     * @param centerId 조회할 센터 ID
     * @return 해당 센터의 등록 회원수
     */
    public int getMemberCount(String centerId) {
        return centerService.getMemberCount(centerId);
    }

    /**
     * 센터별 수업 수 조회
     * @param centerId 조회할 센터 ID
     * @return 해당 센터의 수업 수
     */
    public int getClassCount(String centerId) {
        return centerService.getClassCount(centerId);
    }
}
