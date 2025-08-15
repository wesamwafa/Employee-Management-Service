package org.employee.management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.EmployeeDto;
import org.employee.management.service.EmployeeManagementServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeControllerImpl implements EmployeeController{

    private final EmployeeManagementServiceImpl employeeService;

    private static final Logger logger = LoggerFactory.getLogger(EmployeeControllerImpl.class);

    public EmployeeControllerImpl(EmployeeManagementServiceImpl employeeService) {
        this.employeeService = employeeService;
    }


    @Override
    public ResponseEntity<EmployeeDto> createEmployee(@Valid EmployeeDto employeeDto) throws BusinessException {
        try {
            employeeService.addEmployee(employeeDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (BusinessException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() throws BusinessException {
        return ResponseEntity.ok(employeeService.getAllEmployees());
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
