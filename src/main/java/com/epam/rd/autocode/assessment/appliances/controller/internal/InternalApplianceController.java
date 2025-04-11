package com.epam.rd.autocode.assessment.appliances.controller.internal;

import com.epam.rd.autocode.assessment.appliances.model.dto.appliance.ApplianceCreateDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.appliance.ApplianceEditDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.appliance.ApplianceResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.manufacturer.ManufacturerDTO;
import com.epam.rd.autocode.assessment.appliances.model.enums.Category;
import com.epam.rd.autocode.assessment.appliances.model.enums.PowerType;
import com.epam.rd.autocode.assessment.appliances.service.ApplianceService;
import com.epam.rd.autocode.assessment.appliances.service.ManufacturerService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/internal/appliances")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InternalApplianceController {

    private final ApplianceService service;
    private final ManufacturerService manufacturerService;

    // employee want to see all appliances
    @GetMapping
    public String getAllAppliances(Model model) {
        List<ApplianceResponseDTO> all = service.findAll(0, 100);
        model.addAttribute("appliances", all);
        return "appliance/appliances-for-employee";
    }

    // employee want to see add-appliances page
    @GetMapping("/new")
    public String showForm(Model model) {
        List<ManufacturerDTO> manufacturers = manufacturerService.findAll();
        model.addAttribute("createDTO", new ApplianceCreateDTO());
        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("categories", Category.values());
        model.addAttribute("powerTypes", PowerType.values());
        return "appliance/newAppliance";
    }

    // employee send dto to create new appliance
    @PostMapping
    public String processForm(@Valid @ModelAttribute(name = "createDTO") ApplianceCreateDTO dto,
                              BindingResult bindingResult, Model model) {

        List<ManufacturerDTO> manufacturers = manufacturerService.findAll();

        model.addAttribute("manufacturers", manufacturers);
        model.addAttribute("categories", Category.values());
        model.addAttribute("powerTypes", PowerType.values());

        if (bindingResult.hasErrors()) {

            System.out.println("Has some errors");
            System.out.println(bindingResult.getAllErrors());

            return "appliance/newAppliance";
        }
        try {
            service.create(dto);
            return "redirect:/internal/appliances";
        } catch (EntityNotFoundException e) {
            bindingResult.rejectValue("manufacturerId", "manufacturer.not.exist", e.getMessage());
            return "appliance/newAppliance";
        }
    }

    // employee want to see edit-appliances page
    @GetMapping("/{id}/edit")
    public String editAppliance(@PathVariable("id") Long applianceID, Model model) {
        ApplianceEditDTO dto = service.findByIdToEdit(applianceID);

        model.addAttribute("editAppliance", dto);
        model.addAttribute("categories", Category.values());
        model.addAttribute("powerTypes", PowerType.values());
        return "appliance/editAppliance";
    }

    // employee send edited data to update appliance
    @PutMapping("/{id}/edit")
    public String updateAppliance(@Valid @ModelAttribute(name = "edit_appliance") ApplianceEditDTO dto,
                                  @PathVariable("id") Long applianceId,
                                  BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("powerTypes", PowerType.values());
            return "appliance/editAppliance";
        }
        service.update(applianceId, dto);
        return "redirect:/internal/appliances";
    }
}

