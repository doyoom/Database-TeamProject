package com.powerFitness.dto;

public class ClassDTO {
    private String classId;
    private String centerId;
    private String instructorId;
    private String ctypeId;
    private String placeId;
    private String ctimeId;
    private String className;
    private int classFee;
    private String description;
    private int maxPeople;
    private int currentPeople;
    
    /*
     *  기본 생성자
     */
    public ClassDTO() {}

    /*
     *  모든 필드를 초기화하는 생성자
     */
    public ClassDTO(String classId, String centerId, String instructorId, String ctypeId, String placeId, String ctimeId,
                    String className, int classFee, String description, int maxPeople, int currentPeople) {
        this.classId = classId;
        this.centerId = centerId;
        this.instructorId = instructorId;
        this.ctypeId = ctypeId;
        this.placeId = placeId;
        this.ctimeId = ctimeId;
        this.className = className;
        this.classFee = classFee;
        this.description = description;
        this.maxPeople = maxPeople;
        this.currentPeople = currentPeople;
    }

    /*
     * Getter, Setter 메서드 
     */
    public String getClassId() { return classId; }
    public void setClassId(String classId) { this.classId = classId; }
    public String getCenterId() { return centerId; }
    public void setCenterId(String centerId) { this.centerId = centerId; }
    public String getInstructorId() { return instructorId; }
    public void setInstructorId(String instructorId) { this.instructorId = instructorId; }
    public String getCtypeId() { return ctypeId; }
    public void setCtypeId(String ctypeId) { this.ctypeId = ctypeId; }
    public String getPlaceId() { return placeId; }
    public void setPlaceId(String placeId) { this.placeId = placeId; }
    public String getCtimeId() { return ctimeId; }
    public void setCtimeId(String ctimeId) { this.ctimeId = ctimeId; }
    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }
    public int getClassFee() { return classFee; }
    public void setClassFee(int classFee) { this.classFee = classFee; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getMaxPeople() { return maxPeople; }
    public void setMaxPeople(int maxPeople) { this.maxPeople = maxPeople; }
    public int getCurrentPeople() { return currentPeople; }
    public void setCurrentPeople(int currentPeople) { this.currentPeople = currentPeople; }
}
