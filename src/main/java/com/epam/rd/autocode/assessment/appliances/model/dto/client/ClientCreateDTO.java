package com.epam.rd.autocode.assessment.appliances.model.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientCreateDTO {

    @NotBlank(message = "{validation.name.notBlank}")
    @Size(min = 2, max = 30, message = "{validation.name.size}")
    private String name;

    @NotBlank(message = "{validation.email.invalid}")
    @Email(message = "{validation.email.invalid}")
    private String email;

    @NotBlank(message = "{validation.password.invalid}")
    private String password;
}