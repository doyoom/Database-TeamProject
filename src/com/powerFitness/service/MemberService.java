package com.powerFitness.service;

import com.powerFitness.dao.MemberDAO;
import com.powerFitness.dto.MemberDTO;

import java.util.List;

public class MemberService {

    private MemberDAO memberDAO;

    public MemberService() {
        memberDAO = new MemberDAO();
    }

    /**
     * 회원을 등록합니다.
     *
     * @param member 등록할 회원 정보
     */
    public void registerMember(MemberDTO member) {
        memberDAO.registerMember(member);
    }

    /**
     * 회원 아이디로 회원 정보를 조회합니다.
     *
     * @param memberId 조회할 회원의 아이디
     * @return 조회된 회원 정보
     */
    public MemberDTO getMemberById(String memberId) {
        return memberDAO.getMemberById(memberId);
    }

    /**
     * 회원 정보를 수정합니다.
     *
     * @param member 수정할 회원 정보
     */
    public void updateMember(MemberDTO member) {
        memberDAO.updateMember(member);
    }

    /**
     * 회원의 비밀번호를 변경합니다.
     *
     * @param memberId    비밀번호를 변경할 회원의 아이디
     * @param newPassword 변경할 비밀번호
     */
    public void changePassword(String memberId, String newPassword) {
        memberDAO.changePassword(memberId, newPassword);
    }

    /**
     * 회원을 삭제합니다.
     *
     * @param memberId 삭제할 회원의 아이디
     */
    public void deleteMember(String memberId) {
        memberDAO.deleteMember(memberId);
    }

    /**
     * 키워드로 회원을 검색합니다.
     *
     * @param keyword 검색할 키워드
     * @return 검색된 회원 목록
     */
    public List<MemberDTO> searchMembers(String keyword) {
        return memberDAO.searchMembers(keyword);
    }
    
    /**
     * 센터별 가장 최근 회원 아이디를 조회합니다.
     *
     * @param centerId 센터 아이디
     * @return 가장 최근 회원 아이디
     */
    public String getLastMemberId(String centerId) {
        return memberDAO.getLastMemberId(centerId);
    }

    /**
     * 모든 회원의 정보를 조회합니다.
     *
     * @return 모든 회원 정보 목록
     */
    public List<MemberDTO> getAllMembers() {
        return memberDAO.getAllMembers();
    }
}
