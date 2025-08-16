package org.employee.management.repo;
import org.employee.management.model.entity.DailySummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DailySummaryRepository extends JpaRepository<DailySummary, Long> {

    List<DailySummary> findBySummaryDate(LocalDate summaryDate);

    List<DailySummary> findBySummaryDateOrderByDepartmentName(LocalDate summaryDate);
}


