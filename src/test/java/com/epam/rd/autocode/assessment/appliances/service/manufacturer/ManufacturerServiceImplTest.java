package com.epam.rd.autocode.assessment.appliances.service.impl;

import com.epam.rd.autocode.assessment.appliances.model.self.Manufacturer;
import com.epam.rd.autocode.assessment.appliances.repository.ManufacturerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ManufacturerServiceImplTest {

    @Mock
    private ManufacturerRepository repository;

    @InjectMocks
    private ManufacturerServiceImpl service;


    @Test
    void findByExistsId() {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(432L);
        manufacturer.setName("Hisense");
        manufacturer.setDescription("Hisense, more than a brand");
        manufacturer.setPhoneNumber("1234567890");
    }
}