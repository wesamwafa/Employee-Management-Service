package org.employee.management.service;

import org.employee.management.exception.BusinessException;
import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.model.entity.Department;
import org.employee.management.model.entity.Employee;
import org.employee.management.repo.DepartmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentManagementServiceImplTest {

    private DepartmentRepository departmentRepository;
    private DepartmentManagementServiceImpl departmentManagementService;
    private Department department;
    private DepartmentDto departmentDto;

    @BeforeEach
    void setUp() {
        departmentRepository = mock(DepartmentRepository.class);
        departmentManagementService = new DepartmentManagementServiceImpl(departmentRepository);
        department = new Department();
        department.setId(1L);
        department.setDepartmentName("Engineering");
        departmentDto = DepartmentDto.builder()
                .departmentName("Engineering")
                .build();
    }

    @Test
    void addDepartment_WhenDepartmentDoesNotExist_ShouldSaveDepartment() throws BusinessException {
        when(departmentRepository.existsByDepartmentNameIgnoreCase(anyString())).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        departmentManagementService.addDepartment(departmentDto);
        verify(departmentRepository, times(1)).existsByDepartmentNameIgnoreCase("Engineering");
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void addDepartment_WhenDepartmentAlreadyExists_ShouldThrowBusinessException() {
        when(departmentRepository.existsByDepartmentNameIgnoreCase(anyString())).thenReturn(true);
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            departmentManagementService.addDepartment(departmentDto);
        });
        assertEquals("department already exists", exception.getMessage());
        verify(departmentRepository, times(1)).existsByDepartmentNameIgnoreCase("Engineering");
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void getAllDepartments_WhenDepartmentsExist_ShouldReturnListOfDepartmentDtos() {
        List<Department> departments = Arrays.asList(department);
        when(departmentRepository.findAll()).thenReturn(departments);
        List<DepartmentDto> result = departmentManagementService.getAllDepartments();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Engineering", result.get(0).getDepartmentName());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getAllDepartments_WhenNoDepartmentsExist_ShouldReturnEmptyList() {
        when(departmentRepository.findAll()).thenReturn(Collections.emptyList());
        List<DepartmentDto> result = departmentManagementService.getAllDepartments();
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartment_WhenDepartmentExists_ShouldReturnDepartmentDto() throws BusinessException {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        DepartmentDto result = departmentManagementService.getDepartment("1");
        assertNotNull(result);
        assertEquals("Engineering", result.getDepartmentName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void getDepartment_WhenDepartmentDoesNotExist_ShouldThrowBusinessException() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            departmentManagementService.getDepartment("99");
        });
        assertEquals("Department doesnt exist", exception.getMessage());
        verify(departmentRepository, times(1)).findById(99L);
    }

    @Test
    void updateDepartment_WhenDepartmentExists_ShouldUpdateDepartment() {
        DepartmentDto updatedDto = DepartmentDto.builder().departmentName("Updated Engineering").build();
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentRepository.save(any(Department.class))).thenReturn(department);
        departmentManagementService.updateDepartment("1", updatedDto);
        assertEquals("Updated Engineering", department.getDepartmentName());
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).save(department);
    }

    @Test
    void updateDepartment_WhenDepartmentDoesNotExist_ShouldNotUpdate() {
        DepartmentDto updatedDto = DepartmentDto.builder().departmentName("Updated Engineering").build();
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        departmentManagementService.updateDepartment("99", updatedDto);
        verify(departmentRepository, times(1)).findById(99L);
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void deleteDepartment_WhenNoEmployees_ShouldDeleteDepartment() throws BusinessException {
        department.setEmployees(new ArrayList<>());
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        doNothing().when(departmentRepository).deleteById(1L);
        departmentManagementService.deleteDepartment("1");
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteDepartment_WhenHasEmployees_ShouldThrowBusinessException() {
        department.setEmployees(Arrays.asList(new Employee()));
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            departmentManagementService.deleteDepartment("1");
        });
        assertEquals("Cannot delete department with existing employees", exception.getMessage());
        verify(departmentRepository, times(1)).findById(1L);
        verify(departmentRepository, never()).deleteById(anyLong());
    }

    @Test
    void getDepartmentByName_WhenExists_ShouldReturnDepartment() throws BusinessException {
        when(departmentRepository.findByDepartmentNameIgnoreCase("Engineering")).thenReturn(Optional.of(department));
        Department result = departmentManagementService.getDepartmentByName("Engineering");
        assertNotNull(result);
        assertEquals("Engineering", result.getDepartmentName());
        verify(departmentRepository, times(1)).findByDepartmentNameIgnoreCase("Engineering");
    }

    @Test
    void getDepartmentByName_WhenDoesNotExist_ShouldThrowBusinessException() {
        when(departmentRepository.findByDepartmentNameIgnoreCase(anyString())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            departmentManagementService.getDepartmentByName("NonExistent");
        });
        assertEquals("Department 'NonExistent' does not exist", exception.getMessage());
        verify(departmentRepository, times(1)).findByDepartmentNameIgnoreCase("NonExistent");
    }

    @Test
    void getDepartmentEmployeeCounts_ShouldReturnCounts() {
        List<Object[]> mockCounts = Arrays.asList(
                new Object[]{"Engineering", 5L},
                new Object[]{"HR", 3L}
        );
        when(departmentRepository.findDepartmentEmployeeCounts()).thenReturn(mockCounts);
        List<Object[]> result = departmentManagementService.getDepartmentEmployeeCounts();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
        assertEquals("Engineering", result.get(0)[0]);
        assertEquals(5L, result.get(0)[1]);
        verify(departmentRepository, times(1)).findDepartmentEmployeeCounts();
    }

    @Test
    void getDepartmentById_WhenExists_ShouldReturnDepartment() throws BusinessException {
        when(departmentRepository.findById(1L)).thenReturn(Optional.of(department));
        Department result = departmentManagementService.getDepartmentById(1L);
        assertNotNull(result);
        assertEquals("Engineering", result.getDepartmentName());
        verify(departmentRepository, times(1)).findById(1L);
    }

    @Test
    void getDepartmentById_WhenDoesNotExist_ShouldThrowBusinessException() {
        when(departmentRepository.findById(anyLong())).thenReturn(Optional.empty());
        BusinessException exception = assertThrows(BusinessException.class, () -> {
            departmentManagementService.getDepartmentById(99L);
        });
        assertEquals("Department doesnt exist", exception.getMessage());
        verify(departmentRepository, times(1)).findById(99L);
    }

    @Test
    void DepartmentToDto_ShouldConvertCorrectly() {
        Department dept = new Department();
        dept.setDepartmentName("Test Dept");
        DepartmentDto dto = departmentManagementService.DepartmentToDto(dept);
        assertNotNull(dto);
        assertEquals("Test Dept", dto.getDepartmentName());
    }
}


