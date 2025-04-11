package com.epam.rd.autocode.assessment.appliances;

import com.epam.rd.autocode.assessment.appliances.model.dto.manufacturer.ManufacturerDTO;
import com.epam.rd.autocode.assessment.appliances.model.mappers.ManufacturerMapper;
import com.epam.rd.autocode.assessment.appliances.model.self.Manufacturer;

public class Main {
    public static void main(String[] args) {
        ManufacturerDTO manufacturer = new ManufacturerDTO();
        manufacturer.setName("Manufacturer");
        manufacturer.setDescription("Manufacturer description");
        manufacturer.setPhoneNumber("1234+/567  8 -90");
        manufacturer.setId(123L);

        ManufacturerMapper mapper = ManufacturerMapper.INSTANCE;
        Manufacturer manufacturerREAL = new Manufacturer();
        mapper.updateEntityFromDto(manufacturer, manufacturerREAL);

        System.out.println(manufacturerREAL);

    }
}
