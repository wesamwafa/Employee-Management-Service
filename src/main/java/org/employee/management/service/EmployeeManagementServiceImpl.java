package org.employee.management.service;

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

    private final EmployeeRepository employeeRepository ;

    private final DepartmentManagementService departmentManagementService ;


    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository, DepartmentManagementService departmentManagementService) {
        this.employeeRepository = employeeRepository;
        this.departmentManagementService = departmentManagementService;
    }

    @Override
    public void addEmployee(EmployeeDto employeeDto) throws BusinessException {
        if (employeeDto != null) {
            try {
                if(isEmployeeExist(employeeDto.getStaffId(), employeeDto.getEmail())){
                    throw new BusinessException("employee already exists");
                }
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
    public EmployeeDto getEmployee(String staffId) {
        return null;
    }

    @Override
    public void updateEmployee(String staffId, EmployeeDto employeeDto) {

    }

    @Override
    public void deleteEmployee(String staffId) {

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

    private boolean isEmployeeExist(String staffId , String email){
        return employeeRepository.existsByStaffId(staffId) || employeeRepository.existsByEmail(email);
    }

    private Department getDepartmentByName(String departmentName) throws BusinessException{
        return departmentManagementService.getDepartmentByName(departmentName);
    }

}
