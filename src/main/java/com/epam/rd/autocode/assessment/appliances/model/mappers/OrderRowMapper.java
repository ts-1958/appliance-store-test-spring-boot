package com.epam.rd.autocode.assessment.appliances.model.mappers;

import com.epam.rd.autocode.assessment.appliances.model.dto.order.OrderRowResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.self.OrderRow;
import org.mapstruct.Mapper;

@Mapper
public interface OrderRowMapper {
    OrderRowResponseDTO toResponseDTO(OrderRow orderRow);
}
