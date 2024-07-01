package com.powerFitness.controller;

import com.powerFitness.dto.MemberDTO;
import com.powerFitness.service.MemberService;

import java.util.List;

/**
 * 회원 관련 작업을 담당하는 컨트롤러 클래스입니다.
 */
public class MemberController {

    private MemberService memberService;

    /**
     * 기본 생성자
     */
    public MemberController() {
        memberService = new MemberService();
    }

    /**
     * 회원 등록을 처리합니다.
     * @param member 등록할 회원 정보
     */
    public void registerMember(MemberDTO member) {
        memberService.registerMember(member);
    }

    /**
     * 주어진 회원 ID에 해당하는 회원 정보를 가져옵니다.
     * @param memberId 조회할 회원의 ID
     * @return 회원 정보
     */
    public MemberDTO getMemberById(String memberId) {
        return memberService.getMemberById(memberId);
    }

    /**
     * 회원 정보를 업데이트합니다.
     * @param member 업데이트할 회원 정보
     */
    public void updateMember(MemberDTO member) {
        memberService.updateMember(member);
    }

    /**
     * 회원의 비밀번호를 변경합니다.
     * @param memberId 비밀번호를 변경할 회원의 ID
     * @param newPassword 새로운 비밀번호
     */
    public void changePassword(String memberId, String newPassword) {
        memberService.changePassword(memberId, newPassword);
    }

    /**
     * 주어진 회원 ID에 해당하는 회원을 삭제합니다.
     * @param memberId 삭제할 회원의 ID
     */
    public void deleteMember(String memberId) {
        memberService.deleteMember(memberId);
    }
    
    /**
     * 주어진 센터 ID에 해당하는 마지막 회원 ID를 반환합니다.
     * @param centerId 센터 ID
     * @return 마지막 회원 ID
     */
    public String getLastMemberId(String centerId) {
        return memberService.getLastMemberId(centerId);
    }
    
    /**
     * 주어진 키워드로 회원을 검색합니다.
     * @param keyword 검색할 키워드
     * @return 검색된 회원 목록
     */
    public List<MemberDTO> searchMembers(String keyword) {
        return memberService.searchMembers(keyword);
    }

    /**
     * 모든 회원의 목록을 반환합니다.
     * @return 모든 회원 목록
     */
    public List<MemberDTO> getAllMembers() {
        return memberService.getAllMembers();
    }
}
