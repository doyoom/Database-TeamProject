package com.powerFitness.controller;

import com.powerFitness.dto.EmployeeDTO;
import com.powerFitness.service.EmployeeService;
import java.util.List;

public class EmployeeController {

    private EmployeeService employeeService;

    public EmployeeController() {
        this.employeeService = new EmployeeService();
    }

    /**
     * 직원 추가
     * @param employee 직원 정보 DTO
     */
    public void addEmployee(EmployeeDTO employee) {
        employeeService.addEmployee(employee);
    }

    /**
     * 직원 정보 수정
     * @param employee 수정할 직원 정보 DTO
     */
    public void updateEmployee(EmployeeDTO employee) {
        employeeService.updateEmployee(employee);
    }

    /**
     * 직원 삭제
     * @param employeeId 삭제할 직원의 ID
     */
    public void deleteEmployee(String employeeId) {
        employeeService.deleteEmployee(employeeId);
    }

    /**
     * 직원 정보 조회
     * @param employeeId 조회할 직원의 ID
     * @return 직원 정보 리스트
     */
    public List<EmployeeDTO> getEmployeeDetails(String employeeId) {
        return employeeService.getEmployeeDetails(employeeId);
    }
    
    /**
     * 마지막 직원 아이디 조회
     * @param centerId 센터 ID
     * @return 마지막 직원 아이디
     */
    public String getLastEmployeeId(String centerId) {
        return employeeService.getLastEmployeeId(centerId);
    }
}
