package com.powerFitness.dto;

import java.util.Date;

public class InstructorDTO {
    private String instructorId;
    private String centerId;
    private String name;
    private Date birthday;
    private String phone;
    private char gender;
    private Date join_date;
    private Date quit_date;
    private String address;

    /*
     *  기본 생성자
     */
    public InstructorDTO() {
    }

    /*
     *  모든 필드를 초기화하는 생성자
     */
    public InstructorDTO(String instructorId, String centerId, String name, Date birthday, String phone, char gender, Date join_date, Date quit_date, String address) {
        this.instructorId = instructorId;
        this.centerId = centerId;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
        this.join_date = join_date;
        this.quit_date = quit_date;
        this.address = address;
    }

    /*
     *  Getter 및 Setter 메서드
     */
    
    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Date getJoinDate() {
        return join_date;
    }

    public void setJoinDate(Date join_date) {
        this.join_date = join_date;
    }

    public Date getQuitDate() {
        return quit_date;
    }

    public void setQuitDate(Date quit_date) {
        this.quit_date = quit_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
