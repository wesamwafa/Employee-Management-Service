package org.employee.management.repo;

import org.employee.management.model.dto.EmployeeDto;
import org.employee.management.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {


    @Query("""
                SELECT COUNT(e) > 0
                FROM Employee e
                WHERE e.email = :email OR e.staffId = :staffId
            """)
    boolean existsByEmailOrStaffId(@Param("staffId") String staffId, @Param("email") String email);

    @Query("""
            SELECT new org.employee.management.model.dto.EmployeeDto(
                e.staffId,
                e.name,
                e.email,
                e.department.departmentName,
                CAST(e.hireDate AS string),
                CAST(e.salary AS string)) FROM Employee e""")
    List<EmployeeDto> getAllEmployees();

    Optional<Employee> findByStaffId(@Param("staffId") String staffId);

    void deleteByStaffId(String staffId);
}
