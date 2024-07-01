package com.powerFitness.dto;

public class AttendanceDTO {
    private String attendId;
    
    private String memberId;
    private String memberName;
    
    private String classId;
    private String className;
    
    private String date;
    private String status;
    
    private String payId;
    private String paymentStatus;
    
   private int maxCapacity;
   private int currentEnrollment;

    /*
     * Getter, Setter 메서드 
     */
    public String getAttendId() {
        return attendId;
    }

    public void setAttendId(String attendId) {
        this.attendId = attendId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
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

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

   public int getMaxCapacity() {
       return maxCapacity;
   }

   public void setMaxCapacity(int maxCapacity) {
       this.maxCapacity = maxCapacity;
   }

   public int getCurrentEnrollment() {
       return currentEnrollment;
   }

   public void setCurrentEnrollment(int currentEnrollment) {
       this.currentEnrollment = currentEnrollment;
   }
}