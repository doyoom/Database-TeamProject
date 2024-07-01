package com.powerFitness.service;

import com.powerFitness.dao.EmployeeDAO;
import com.powerFitness.dto.EmployeeDTO;
import java.util.List;

public class EmployeeService {

    private EmployeeDAO employeeDAO;

    public EmployeeService() {
        this.employeeDAO = new EmployeeDAO();
    }

    /**
     * 직원을 추가합니다.
     *
     * @param employee 추가할 직원 정보
     */
    public void addEmployee(EmployeeDTO employee) {
        employeeDAO.addEmployee(employee);
    }

    /**
     * 직원 정보를 수정합니다.
     *
     * @param employee 수정할 직원 정보
     */
    public void updateEmployee(EmployeeDTO employee) {
        employeeDAO.updateEmployee(employee);
    }

    /**
     * 직원을 삭제합니다.
     *
     * @param employeeId 삭제할 직원의 ID
     */
    public void deleteEmployee(String employeeId) {
        employeeDAO.deleteEmployee(employeeId);
    }

    /**
     * 직원의 정보를 조회합니다.
     *
     * @param employeeId 직원 ID
     * @return 직원 정보 목록
     */
    public List<EmployeeDTO> getEmployeeDetails(String employeeId) {
        return employeeDAO.getEmployeeDetails(employeeId);
    }
    
    /**
     * 센터별 마지막 직원 아이디를 조회합니다.
     *
     * @param centerId 센터 ID
     * @return 마지막 직원 아이디
     */
    public String getLastEmployeeId(String centerId) {
        return employeeDAO.getLastEmployeeId(centerId);
    }
}
