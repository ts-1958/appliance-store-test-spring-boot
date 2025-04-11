package com.epam.rd.autocode.assessment.appliances.model.dto.appliance;

import com.epam.rd.autocode.assessment.appliances.model.enums.Category;
import com.epam.rd.autocode.assessment.appliances.model.enums.PowerType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceCreateDTO {

    // todo constraint
    Long id;

    @NotBlank(message = "{validation.name.notBlank}")
    @Size(max = 30, message = "{validation.name.size}")
    private String name;

    @NotNull(message = "{validation.category.invalid}")
    private Category category;

    @NotBlank(message = "{validation.model.invalid}")
    @Size(max = 30, message = "{validation.model.invalid}")
    private String model;

    @NotNull(message = "{validation.manufacturer.not.selected}")
    private Long manufacturerId;

    @NotNull(message = "{validation.power-type.not.selected}")
    private PowerType powerType;

    @NotBlank(message = "{validation.characteristic.not.selected}")
    private String characteristic;
    private String description;

    @NotNull(message = "{validation.power.not.selected}")
    private Integer power;

    @NotNull(message = "{validation.price.not.selected}")
    @DecimalMin(value = "0.00", message = "{validation.price.can-not-be.negative}")
    private BigDecimal price;

    @DecimalMin(value = "0.00", message = "{validation.discount.can-not-be.negative}")
    @DecimalMax(value = "100.00", message = "{validation.discount.can-not-be.exceed-hundred}")
    private BigDecimal discount;

    @Override
    public String toString() {
        return "CreateApplianceDTO{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", powerType=" + powerType +
                ", power=" + power +
                ", price=" + price +
                '}';
    }
}

