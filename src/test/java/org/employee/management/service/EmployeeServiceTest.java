package org.employee.management.service;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.EmployeeDto;
import org.employee.management.model.entity.Department;
import org.employee.management.model.entity.Employee;
import org.employee.management.repo.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class EmployeeManagementServiceImplTest {

    private EmployeeRepository employeeRepository;
    private DepartmentManagementService departmentService;
    private EmployeeManagementServiceImpl service;

    @BeforeEach
    void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        departmentService = mock(DepartmentManagementService.class);
        service = new EmployeeManagementServiceImpl(employeeRepository, departmentService);
    }

    @Test
    void addEmployee_success() throws BusinessException {
        EmployeeDto dto = EmployeeDto.builder()
                .staffId("S1")
                .email("test@test.com")
                .name("wesam")
                .hireDate(LocalDate.now().toString())
                .salary("5000")
                .department("IT")
                .build();
        when(employeeRepository.existsByEmailOrStaffId("S1", "test@test.com"))
                .thenReturn(false);
        when(departmentService.getDepartmentByName("IT"))
                .thenReturn(new Department(1L, "IT", new ArrayList<>()));
        when(employeeRepository.save(any(Employee.class)))
                .thenAnswer(invocation -> {
                    Employee e = invocation.getArgument(0);
                    e.setId(100L);
                    return e;
                });

        service.addEmployee(dto);

        ArgumentCaptor<Employee> captor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(captor.capture());

        Employee saved = captor.getValue();
        assertThat(saved.getName()).isEqualTo("wesam");
        assertThat(saved.getDepartment().getDepartmentName()).isEqualTo("IT");
    }

    @Test
    void addEmployee_whenExists_shouldThrow() {
        EmployeeDto dto = EmployeeDto.builder()
                .staffId("S1")
                .email("test@company.com")
                .build();

        when(employeeRepository.existsByEmailOrStaffId("S1", "test@company.com"))
                .thenReturn(true);

        assertThatThrownBy(() -> service.addEmployee(dto))
                .isInstanceOf(BusinessException.class)
                .hasMessage("employee already exists");

        verify(employeeRepository, never()).save(any());
    }

    @Test
    void getEmployee_success() throws BusinessException {
        Department dep = new Department(1L, "HR", new ArrayList<>());
        Employee employee = Employee.builder()
                .id(1L)
                .staffId("S1")
                .name("wesam")
                .email("wesam@test.com")
                .hireDate(LocalDate.now())
                .salary(6000.0)
                .department(dep)
                .build();

        when(employeeRepository.findByStaffId("S1"))
                .thenReturn(Optional.of(employee));

        EmployeeDto dto = service.getEmployee("S1");

        assertThat(dto.getName()).isEqualTo("wesam");
        assertThat(dto.getDepartment()).isEqualTo("HR");
        assertThat(dto.getSalary()).isEqualTo("6000.0");
    }

    @Test
    void getEmployee_notFound_shouldThrow() {
        when(employeeRepository.findByStaffId("X"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getEmployee("X"))
                .isInstanceOf(BusinessException.class)
                .hasMessage("employee does not exist");
    }

    @Test
    void updateEmployee_success() throws BusinessException {
        Department oldDep = new Department(1L, "HR", new ArrayList<>());
        Employee existing = Employee.builder()
                .id(1L)
                .staffId("S1")
                .name("OldName")
                .email("old@company.com")
                .hireDate(LocalDate.now())
                .salary(4000.0)
                .department(oldDep)
                .build();

        when(employeeRepository.findByStaffId("S1"))
                .thenReturn(Optional.of(existing));
        when(departmentService.getDepartmentByName("IT"))
                .thenReturn(new Department(2L, "IT", new ArrayList<>()));

        EmployeeDto updateDto = EmployeeDto.builder()
                .name("NewName")
                .email("new@company.com")
                .department("IT")
                .salary("7000")
                .staffId("S1")
                .hireDate(LocalDate.now().toString())
                .build();

        service.updateEmployee("S1", updateDto);

        verify(employeeRepository).save(existing);
        assertThat(existing.getName()).isEqualTo("NewName");
        assertThat(existing.getEmail()).isEqualTo("new@company.com");
        assertThat(existing.getDepartment().getDepartmentName()).isEqualTo("IT");
        assertThat(existing.getSalary()).isEqualTo(7000.0);
    }

    @Test
    void deleteEmployee_success() throws BusinessException {
        service.deleteEmployee("S1");
        verify(employeeRepository).deleteByStaffId("S1");
    }
}
