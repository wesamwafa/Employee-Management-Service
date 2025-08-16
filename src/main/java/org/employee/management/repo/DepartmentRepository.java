package org.employee.management.repo;

import org.employee.management.model.dto.DepartmentDto;
import org.employee.management.model.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByDepartmentNameIgnoreCase(String name);

    @Query("""
       SELECT new org.employee.management.model.dto.DepartmentDto(
           d.departmentName
       )
       FROM Department d
       """)
    List<DepartmentDto> getAllDepartments();

    boolean existsByDepartmentNameIgnoreCase(String departmentName);

    @Query("SELECT d.departmentName, COUNT(e) FROM Department d LEFT JOIN d.employees e GROUP BY d.id, d.departmentName")
    List<Object[]> findDepartmentEmployeeCounts();
}
