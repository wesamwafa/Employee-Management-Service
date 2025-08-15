package org.employee.management.service;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.model.entity.Department;
import org.employee.management.repo.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentManagementServiceImpl implements DepartmentManagementService {

    private final DepartmentRepository departmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(DepartmentManagementServiceImpl.class);


    public DepartmentManagementServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public void addDepartment(DepartmentDto departmentDto) throws BusinessException {
        if (isDepartmentExist(departmentDto)) {
            throw new BusinessException("department already exists");
        }
        Department department = new Department();
        department.setDepartmentName(departmentDto.getDepartmentName());
        departmentRepository.save(department);
        logger.info("department {} saved successfully ", department.getDepartmentName());
    }

    @Override
    public List<DepartmentDto> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public DepartmentDto getDepartment(DepartmentDto departmentDto) {
        return null;
    }

    @Override
    public void updateDepartment(DepartmentDto departmentDto) {

    }

    @Override
    public void deleteDepartment(DepartmentDto departmentDto) {

    }

    @Override
    public Department getDepartmentByName(String departmentName) throws BusinessException {
        return departmentRepository.findByDepartmentName(departmentName)
                .orElseThrow(() -> new BusinessException("Department '" + departmentName + "' does not exist"));
    }

    private boolean isDepartmentExist(DepartmentDto departmentDto) {
        return departmentRepository.existsByDepartmentName(departmentDto.getDepartmentName());
    }
}
