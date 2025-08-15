package org.employee.management.controller;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/departments")
public interface DepartmentController {

    @PostMapping
    ResponseEntity<DepartmentDto> createDepartment(@RequestBody DepartmentDto departmentDto) throws BusinessException;

    @GetMapping
    ResponseEntity<List<DepartmentDto>> getAllDepartments() throws BusinessException;

    @GetMapping("/{departmentId}")
    ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable("departmentId") String departmentId) throws BusinessException;

    @PutMapping("/{departmentId}")
    ResponseEntity<DepartmentDto> updateDepartment(@PathVariable("departmentId") String departmentId, @RequestBody DepartmentDto departmentDto) throws BusinessException;

    @DeleteMapping("/{departmentId}")
    ResponseEntity<Void> deleteDepartment(@PathVariable("departmentId") String departmentId) throws BusinessException;
}
