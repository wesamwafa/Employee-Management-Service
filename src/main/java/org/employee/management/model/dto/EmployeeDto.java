package org.employee.management.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class EmployeeDto {

    private String staffId;
    private String name;
    private String email;
    private String department;
    private String hireDate;
    private String salary;

}
