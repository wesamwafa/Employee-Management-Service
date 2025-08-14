package org.employee.management.controller;

import lombok.RequiredArgsConstructor;
import org.employee.management.model.dto.EmployeeDto;
import org.employee.management.model.entity.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController{

    @Override
    public ResponseEntity<EmployeeDto> createEmployee(EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        return null;
    }

    @Override
    public ResponseEntity<EmployeeDto> getEmployeeById(Long staffId) {
        return null;
    }

    @Override
    public ResponseEntity<EmployeeDto> updateEmployee(Long staffId, EmployeeDto employeeDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long staffId) {
        return null;
    }
}
