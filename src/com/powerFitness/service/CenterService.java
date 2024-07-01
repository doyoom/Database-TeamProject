package com.powerFitness.service;

import com.powerFitness.dao.CenterDAO;
import com.powerFitness.dto.CenterDTO;

import java.util.List;

public class CenterService {
    private CenterDAO centerDAO;

    public CenterService() {
        this.centerDAO = new CenterDAO();
    }

    /**
     * 센터 정보를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 센터 정보 리스트
     */
    public List<CenterDTO> getCenterDetails(String centerId) {
        return centerDAO.getCenterDetails(centerId);
    }

    /**
     * 센터 정보를 수정합니다.
     *
     * @param center 수정할 센터 정보
     */
    public void updateCenter(CenterDTO center) {
        centerDAO.updateCenter(center);
    }

    /**
     * 센터 내 등록된 회원 수를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 회원 수
     */
    public int getMemberCount(String centerId) {
        return centerDAO.getMemberCount(centerId);
    }

    /**
     * 센터별 수업 수를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 수업 수
     */
    public int getClassCount(String centerId) {
        return centerDAO.getClassCount(centerId);
    }
}
