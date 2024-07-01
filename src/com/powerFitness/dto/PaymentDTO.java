package com.powerFitness.dto;

public class PaymentDTO {
    private String payId;
    private String payType;
    private int payFee;
    private String date;
    private String status;
    private String memberName; // 뷰 활용을 위해 회원 이름 필드 추가
    private String memberId; // 뷰 활용을 위해 id 필드 추가 

	/*
	 *  Getter 및 Setter 메서드
	 */
    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getPayFee() {
        return payFee;
    }

    public void setPayFee(int payFee) {
        this.payFee = payFee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMemberName() { 
        return memberName;
    }

    public void setMemberName(String memberName) { 
        this.memberName = memberName;
    }
    
    public String getMemberId() { 
        return memberId;
    }

    public void setMemberId(String memberId) { 
        this.memberId = memberId;
    }
}
