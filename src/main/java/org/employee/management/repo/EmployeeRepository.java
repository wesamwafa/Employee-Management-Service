package org.employee.management.repo;

import org.employee.management.model.dto.EmployeeDto;
import org.employee.management.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    boolean existsByStaffId(String staffId);

    boolean existsByEmail(String email);

    @Query("""
        SELECT new org.employee.management.model.dto.EmployeeDto(
            e.staffId,
            e.name,
            e.email,
            e.department.departmentName,
            CAST(e.hireDate AS string),
            CAST(e.salary AS string)) FROM Employee e""")
    List<EmployeeDto> getAllEmployees();
}
