package org.employee.management.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DepartmentDto {

    @NotBlank(message = "Department Name is required")
    @Size(min = 1 , max = 15, message = "Department name cannot exceed 15 characters")
    private String departmentName;

}
