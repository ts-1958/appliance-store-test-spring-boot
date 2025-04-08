package com.epam.rd.autocode.assessment.appliances.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Employee extends User {
    private String department;

    public Employee(Long id, String name, String email, String password, String department) {
        super(id, name, email, password);
        setDepartment(department);
    }
}
