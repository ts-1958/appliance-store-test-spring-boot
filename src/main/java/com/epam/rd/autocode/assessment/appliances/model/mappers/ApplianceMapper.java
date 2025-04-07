package com.epam.rd.autocode.assessment.appliances.model.mappers;

import com.epam.rd.autocode.assessment.appliances.model.dto.appliance.ApplianceCreateDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.appliance.ApplianceResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.self.Appliance;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ManufacturerMapper.class)
public interface ApplianceMapper {
    ApplianceMapper INSTANCE = Mappers.getMapper(ApplianceMapper.class);

    ApplianceResponseDTO toResponseDTO(Appliance appliance);
    Appliance toEntity(ApplianceCreateDTO dto);
}
