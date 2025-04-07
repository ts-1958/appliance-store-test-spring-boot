package com.epam.rd.autocode.assessment.appliances.model.dto.order;

import jakarta.validation.Valid;
import lombok.Data;

import java.util.Set;

@Data
public class OrderCreateDTO {

    /**
     * It tells Validator to go through each object in the
     * collection and apply its own validation annotations to it.
     */
    @Valid
    private Set<OrderRowCreateDTO> orderRowSet;
}
