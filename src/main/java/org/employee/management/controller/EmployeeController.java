package org.employee.management.controller;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/employees")
public interface EmployeeController {


    @PostMapping
    ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) throws BusinessException;

    @GetMapping
    ResponseEntity<List<EmployeeDto>> getAllEmployees() throws BusinessException;

    @GetMapping("/{staffId}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable("staffId") String staffId) throws BusinessException;

    @PutMapping("/{staffId}")
    ResponseEntity<EmployeeDto> updateEmployee(@PathVariable("staffId") String staffId, @RequestBody EmployeeDto employeeDto) throws BusinessException;

    @DeleteMapping("/{staffId}")
    ResponseEntity<Void> deleteEmployee(@PathVariable("staffId") String staffId) throws BusinessException;
}
