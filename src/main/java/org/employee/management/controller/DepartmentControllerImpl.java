package org.employee.management.controller;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class DepartmentControllerImpl implements DepartmentController {
    @Override
    public ResponseEntity<DepartmentDto> createDepartment(DepartmentDto departmentDto) throws BusinessException {
        return null;
    }

    @Override
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() throws BusinessException {
        return null;
    }

    @Override
    public ResponseEntity<DepartmentDto> getDepartmentById(String departmentId) throws BusinessException {
        return null;
    }

    @Override
    public ResponseEntity<DepartmentDto> updateDepartment(String departmentId, DepartmentDto departmentDto) throws BusinessException {
        return null;
    }

    @Override
    public ResponseEntity<Void> deleteDepartment(String departmentId) throws BusinessException {
        return null;
    }
}
