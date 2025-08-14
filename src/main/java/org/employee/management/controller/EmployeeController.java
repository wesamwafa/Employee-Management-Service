package org.employee.management.controller;

import org.employee.management.model.dto.EmployeeDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/employees")
public interface EmployeeController {


    @PostMapping
    ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto);

    @GetMapping
    ResponseEntity<List<EmployeeDto>> getAllEmployees();

    @GetMapping("/{staffId}")
    ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long staffId);

    @PutMapping("/{staffId}")
    ResponseEntity<EmployeeDto> updateEmployee(@PathVariable Long staffId, @RequestBody EmployeeDto employeeDto);

    @DeleteMapping("/{staffId}")
    ResponseEntity<Void> deleteEmployee(@PathVariable Long staffId);
}
