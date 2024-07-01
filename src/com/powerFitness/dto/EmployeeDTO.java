package com.powerFitness.dto;

import java.util.Date;

public class EmployeeDTO {
    private String employeeId;
    private String centerId;
    private String name;
    private Date birthday;
    private String phone;
    private char gender;
    private Date joinDate;
    private Date quitDate;
    private String address;
    private String password;
    private String title;

    /*
     *  기본 생성자
     */
    public EmployeeDTO() {
    }

    /*
     *  모든 필드를 초기화하는 생성자
     */
    public EmployeeDTO(String employeeId, String centerId, String name, Date birthday, String phone, char gender, Date joinDate, Date quitDate, String address, String password, String title) {
        this.employeeId = employeeId;
        this.centerId = centerId;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
        this.joinDate = joinDate;
        this.quitDate = quitDate;
        this.address = address;
        this.password = password;
        this.title = title;
    }

	/*
	 *  Getter 및 Setter 메서드
	 */
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
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
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    public Date getQuitDate() {
        return quitDate;
    }

    public void setQuitDate(Date quitDate) {
        this.quitDate = quitDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}