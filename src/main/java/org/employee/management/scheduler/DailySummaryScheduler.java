package org.employee.management.scheduler;


import org.employee.management.model.entity.DailySummary;
import org.employee.management.repo.DailySummaryRepository;
import org.employee.management.service.DepartmentManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DailySummaryScheduler {

    private static final Logger logger = LoggerFactory.getLogger(DailySummaryScheduler.class);

    private final DepartmentManagementService departmentManagementService;
    private final DailySummaryRepository dailySummaryRepository;

    @Autowired
    public DailySummaryScheduler(DepartmentManagementService departmentManagementService,
                                 DailySummaryRepository dailySummaryRepository) {
        this.departmentManagementService = departmentManagementService;
        this.dailySummaryRepository = dailySummaryRepository;
    }

    @Scheduled(cron = "0 0 9 * * *")
    public void generateDailySummary() {
        logger.info("Starting daily summary generation at {}", LocalDate.now());
        try {
            List<Object[]> departmentCounts = departmentManagementService.getDepartmentEmployeeCounts();
            for (Object[] result : departmentCounts) {
                String departmentName = (String) result[0];
                Long employeeCount = (Long) result[1];
                DailySummary summary = new DailySummary();
                summary.setDepartmentName(departmentName);
                summary.setSummaryDate(LocalDate.now());
                summary.setEmployeeCount(employeeCount);
                dailySummaryRepository.save(summary);
            }
            logger.info("report completed successfully");
        } catch (Exception e) {
            logger.error("Error generating daily report : {}", e.getMessage(), e);
        }
    }
}

