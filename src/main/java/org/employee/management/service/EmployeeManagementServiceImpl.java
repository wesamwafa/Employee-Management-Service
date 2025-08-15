package org.employee.management.service;

import jakarta.transaction.Transactional;
import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.EmployeeDto;
import org.employee.management.model.entity.Department;
import org.employee.management.model.entity.Employee;
import org.employee.management.repo.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service

public class EmployeeManagementServiceImpl implements EmployeeManagementService {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeManagementServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    private final DepartmentManagementService departmentManagementService;


    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository, DepartmentManagementService departmentManagementService) {
        this.employeeRepository = employeeRepository;
        this.departmentManagementService = departmentManagementService;
    }

    @Override
    public void addEmployee(EmployeeDto employeeDto) throws BusinessException {
        if (employeeDto != null) {
            try {
                checkEmployeeExist(employeeDto.getStaffId(), employeeDto.getEmail());
                Employee employee = mapDtoToEmployee(employeeDto);
                Employee savedEmployee = employeeRepository.save(employee);
                logger.info("Employee saved successfully with id: {}", savedEmployee.getId());
            } catch (Exception e) {
                throw new BusinessException(e.getMessage());
            }
        }
    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.getAllEmployees();
    }

    @Override
    public EmployeeDto getEmployee(String staffId) throws BusinessException {
        Employee employee = employeeRepository.findByStaffId(staffId).orElseThrow(() -> new BusinessException("employee does not exist"));
        return EmployeeDto.builder()
                .staffId(staffId)
                .salary(String.valueOf(employee.getSalary()))
                .department(employee.getDepartment().getDepartmentName())
                .email(employee.getEmail())
                .hireDate(employee.getHireDate().toString())
                .name(employee.getName())
                .build();
    }

    @Override
    public void updateEmployee(String staffId, EmployeeDto updatedEmployeeDto) throws BusinessException {
        //find employee or throw exception if not exist
        Employee existingEmployee = getByStaffId(staffId);
        updateEmployeeFromDto(existingEmployee,updatedEmployeeDto);
        employeeRepository.save(existingEmployee);
        logger.info("Employee updated successfully");
    }

    @Override
    @Transactional
    public void deleteEmployee(String staffId) throws BusinessException {
        try {
            employeeRepository.deleteByStaffId(staffId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    private Employee mapDtoToEmployee(EmployeeDto employeeDto) throws BusinessException {
        return Employee.builder()
                .name(employeeDto.getName())
                .email(employeeDto.getEmail())
                .hireDate(LocalDate.parse(employeeDto.getHireDate()))
                .staffId(employeeDto.getStaffId())
                .salary(Double.valueOf(employeeDto.getSalary()))
                .department(getDepartmentByName(employeeDto.getDepartment()))
                .build();
    }

    private Employee getByStaffId(String staffId) throws BusinessException {
        return employeeRepository.findByStaffId(staffId).orElseThrow(() -> new BusinessException("employee does not exist"));
    }

    private void checkEmployeeExist(String staffId, String email) throws BusinessException {
        if (employeeRepository.existsByEmailOrStaffId(staffId, email)) {
            throw new BusinessException("employee already exists");
        }
    }

    private void updateEmployeeFromDto(Employee employee, EmployeeDto employeeDto) throws BusinessException {
        if (employeeDto.getName() != null) {
            employee.setName(employeeDto.getName());
        }
        if (employeeDto.getEmail() != null) {
            employee.setEmail(employeeDto.getEmail());
        }
        if (employeeDto.getDepartment() != null) {
            Department department = departmentManagementService.getDepartmentByName(employeeDto.getDepartment()) ;
            employee.setDepartment(department);
        }
        if (employeeDto.getHireDate() != null) {
            employee.setHireDate(LocalDate.parse(employeeDto.getHireDate()));
        }
        if (employeeDto.getSalary() != null) {
            employee.setSalary(Double.valueOf(employeeDto.getSalary()));
        }
        if (employeeDto.getStaffId() != null) {
            employee.setStaffId(employeeDto.getStaffId());
        }
    }

    private Department getDepartmentByName(String departmentName) throws BusinessException {
        return departmentManagementService.getDepartmentByName(departmentName);
    }

}
