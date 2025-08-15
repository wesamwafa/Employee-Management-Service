package org.employee.management.model.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class EmployeeDto {

    @NotBlank(message = "Staff ID is required")
    @Size(max = 9, message = "Staff ID must be at most 9 characters")
    private String staffId;

    @NotBlank(message = "Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters")
    private String name;

    @NotBlank(message = "valid Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Hire date is required")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Hire date must be in the format yyyy-MM-dd")
    private String hireDate;

    @NotBlank(message = "Salary is required")
    @Pattern(regexp = "\\d+(\\.\\d{1,2})?", message = "Salary must be a valid number")
    private String salary;
}
