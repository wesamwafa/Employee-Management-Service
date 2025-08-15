package org.employee.management.service;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.EmployeeDto;

import java.util.List;

public interface EmployeeManagementService {

    void addEmployee(EmployeeDto employeeDto) throws BusinessException;

    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployee(String staffId);

    void updateEmployee(String staffId, EmployeeDto employeeDto);

    void deleteEmployee(String staffId);

}
