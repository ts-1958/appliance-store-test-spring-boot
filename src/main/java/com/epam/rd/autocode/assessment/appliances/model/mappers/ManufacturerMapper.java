package com.epam.rd.autocode.assessment.appliances.model.mappers;

import com.epam.rd.autocode.assessment.appliances.model.dto.manufacturer.ManufacturerDTO;
import com.epam.rd.autocode.assessment.appliances.model.self.Manufacturer;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ManufacturerMapper {

    ManufacturerMapper INSTANCE = Mappers.getMapper(ManufacturerMapper.class);

    ManufacturerDTO toDTO(Manufacturer manufacturer);
    Manufacturer toEntity(ManufacturerDTO dto);

    void updateEntityFromDto(ManufacturerDTO dto, @MappingTarget Manufacturer manufacturer);

    @AfterMapping
    default void normalizePhone(@MappingTarget Manufacturer target) {
        if (target != null) {
            target.setPhoneNumber(target.getPhoneNumber().replaceAll("[^0-9]", ""));
        }
    }
}
