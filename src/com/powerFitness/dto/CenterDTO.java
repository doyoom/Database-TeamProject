package com.powerFitness.dto;

public class CenterDTO {
    private String centerId;
    private String name;
    private String address;
    private String phone;
    private String business_hour;
    private String description;


    /*
     * 생성자 함수 
     */
    public CenterDTO() {
    }

    /*
     *  모든 필드를 초기화하는 생성자
     */
    public CenterDTO(String centerId, String name, String address, String phone, String business_hour, String description) {
        this.centerId = centerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.business_hour = business_hour;
        this.description = description;
    }

    /*
     * Getter, Setter 메서드 
     */
    public String getCenterId() {
        return centerId;
    }

    public void setCenterId(String centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBusinessHour() {
        return business_hour;
    }

    public void setBusinessHour(String business_hour) {
        this.business_hour = business_hour;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

