package org.employee.management.service;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.model.entity.Department;

import java.util.List;

public interface DepartmentManagementService {

    void addDepartment(DepartmentDto departmentDto);

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartment(DepartmentDto departmentDto);

    void updateDepartment(DepartmentDto departmentDto);

    void deleteDepartment(DepartmentDto departmentDto);

    Department getDepartmentByName(String departmentName) throws BusinessException;
}
