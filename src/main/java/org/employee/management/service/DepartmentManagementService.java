package org.employee.management.service;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.model.entity.Department;

import java.util.List;

public interface DepartmentManagementService {

    void addDepartment(DepartmentDto departmentDto) throws BusinessException;

    List<DepartmentDto> getAllDepartments();

    DepartmentDto getDepartment(String departmentId) throws BusinessException;

    void updateDepartment(String departmentId , DepartmentDto departmentDto) throws BusinessException ;

    void deleteDepartment(String departmentId) throws BusinessException;

    Department getDepartmentByName(String departmentName) throws BusinessException;
}
