package org.employee.management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.PrivateKey;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class DepartmentDto {
    private String departmentName;

}
