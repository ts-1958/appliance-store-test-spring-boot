package com.epam.rd.autocode.assessment.appliances.model.dto.manufacturer;

import com.epam.rd.autocode.assessment.appliances.model.validation.PhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ManufacturerDTO {
    private Long id;

    @NotBlank(message = "{validation.name.notBlank}")
    @Size(max = 60, message = "{validation.manufacturer.name.size}")
    private String name;

    @NotBlank(message = "{validation.manufacturer.description.notBlank}")
    @Size(min = 5, max = 255, message = "{validation.manufacturer.description.notBlank}")
    private String description;

    @NotBlank(message = "{validation.phone.notBlank}")
    @PhoneNumber(message = "{validation.phone.invalid}")
    private String phoneNumber;
}
