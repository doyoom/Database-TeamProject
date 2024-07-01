package com.powerFitness.dto;
import java.sql.Date;

public class MemberDTO {
    private String memberid;   // VARCHAR
    private String centerid;   // VARCHAR
    private String name;       // VARCHAR
    private Date birthday;     // DATE
    private String phone;      // VARCHAR
    private char gender;       // CHAR
    private Date joinDate;     // DATE
    private Date quitDate;     // DATE
    private String address;    // VARCHAR
    private String password;   // VARCHAR


		/*
		 *  모든 필드를 초기화하는 생성자
		 */
    public MemberDTO(String memberid, String centerid, String name, Date birthday, String phone, char gender, Date joinDate, Date quitDate, String address, String password) {
        this.memberid = memberid;
        this.centerid = centerid;
        this.name = name;
        this.birthday = birthday;
        this.phone = phone;
        this.gender = gender;
        this.joinDate = joinDate;
        this.quitDate = quitDate;
        this.address = address;
        this.password = password;
    }


    public MemberDTO() {
		// TODO Auto-generated constructor stub
	}


	/*
	 *  Getter 및 Setter 메서드
	 */
    public String getMemberId() {
        return memberid;
    }

    public void setMemberId(String memberid) {
        this.memberid = memberid;
    }

    public String getCenterId() {
        return centerid;
    }

    public void setCenterId(String centerid) {
        this.centerid = centerid;
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
}