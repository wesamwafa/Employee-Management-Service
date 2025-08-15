package org.employee.management.controller;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.service.DepartmentManagementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DepartmentControllerImpl implements DepartmentController {
    private final DepartmentManagementService departmentManagementService ;

    public DepartmentControllerImpl(DepartmentManagementService departmentManagementService) {
        this.departmentManagementService = departmentManagementService;
    }

    @Override
    public ResponseEntity<DepartmentDto> createDepartment(DepartmentDto departmentDto) throws BusinessException {
       departmentManagementService.addDepartment(departmentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() throws BusinessException {
        return ResponseEntity.ok(departmentManagementService.getAllDepartments());
    }

    @Override
    public ResponseEntity<DepartmentDto> getDepartmentById(String departmentId) throws BusinessException {
        return ResponseEntity.ok(departmentManagementService.getDepartment(departmentId));
    }

    @Override
    public ResponseEntity<DepartmentDto> updateDepartment(String departmentId, DepartmentDto departmentDto) throws BusinessException {
        departmentManagementService.updateDepartment(departmentId,departmentDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> deleteDepartment(String departmentId) throws BusinessException {
        departmentManagementService.deleteDepartment(departmentId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
