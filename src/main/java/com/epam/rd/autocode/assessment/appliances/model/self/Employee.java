package com.epam.rd.autocode.assessment.appliances.model.self;

import com.epam.rd.autocode.assessment.appliances.model.enums.Role;
import com.epam.rd.autocode.assessment.appliances.model.enums.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)

@Entity
public class Employee extends User {
    private String department;
    private LocalDateTime temporaryPasswordExpiryTime;
    private String setupPasswordToken;
    private UserStatus status;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private List<Order> orders;

    public Employee(){
        setStatus(UserStatus.NEED_PASSWORD_RESET);
        setRole(Role.MANAGER);
    }

    // todo remove
    public Employee(String name, String email, String department, String password) {
        setEmail(email);
        setPassword(password);
        setStatus(UserStatus.NEED_PASSWORD_RESET);
        setRole(Role.MANAGER);
        setName(name);
        setDepartment(department);
        setSetupPasswordToken("");
    }
}
