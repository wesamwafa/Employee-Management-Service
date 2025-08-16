package org.employee.management.service;

import jakarta.transaction.Transactional;
import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.model.entity.Department;
import org.employee.management.repo.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        List<Department> departments = departmentRepository.findAll();
        if (!departments.isEmpty()) {
            departmentDtos = departments.stream().map(this::DepartmentToDto).collect(Collectors.toList());
        }
        return departmentDtos;
    }

    @Override
    public DepartmentDto getDepartment(String departmentId) throws BusinessException {
        return DepartmentToDto(getDepartmentById(Long.valueOf(departmentId)));
    }

    @Override
    public void updateDepartment(String departmentId, DepartmentDto departmentDto) {
        Optional<Department> existingDepartment = departmentRepository.findById(Long.valueOf(departmentId));
        if (existingDepartment.isPresent()) {
            {
                Department department = existingDepartment.get();
                department.setDepartmentName(departmentDto.getDepartmentName());
                departmentRepository.save(department);
                logger.info("department updated successfully");

            }
        }
    }

    @Override
    @Transactional
    public void deleteDepartment(String departmentId) throws BusinessException {
        try {
            Department department = getDepartmentById(Long.valueOf(departmentId));
            if (!CollectionUtils.isEmpty(department.getEmployees())) {
                throw new BusinessException("Cannot delete department with existing employees");
            }
            departmentRepository.deleteById(Long.valueOf(departmentId));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Override
    public Department getDepartmentByName(String departmentName) throws BusinessException {
        return departmentRepository.findByDepartmentNameIgnoreCase(departmentName.trim())
                .orElseThrow(() -> new BusinessException("Department '" + departmentName + "' does not exist"));
    }

    @Override
    public List<Object[]> getDepartmentEmployeeCounts() {
        logger.debug("Fetching department employee counts");
        return departmentRepository.findDepartmentEmployeeCounts();
    }

    private boolean isDepartmentExist(DepartmentDto departmentDto) {
        return departmentRepository.existsByDepartmentNameIgnoreCase(departmentDto.getDepartmentName().trim());
    }

    public Department getDepartmentById(Long id) throws BusinessException {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Department doesnt exist"));
    }

    DepartmentDto DepartmentToDto(Department department) {
        return DepartmentDto.builder()
                .departmentName(department.getDepartmentName())
                .build();
    }
}
