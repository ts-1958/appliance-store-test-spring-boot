package com.epam.rd.autocode.assessment.appliances.controller.client;

import com.epam.rd.autocode.assessment.appliances.model.dto.appliance.ApplianceResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.enums.Category;
import com.epam.rd.autocode.assessment.appliances.service.ApplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ApplianceController {

    private final ApplianceService service;

    // client want to see all appliances
    @GetMapping("/appliances")
    public String getAllAppliances(
            @RequestParam(required = false) Category category,
            @RequestParam(required = false, defaultValue = "price,asc") String sort,
            @RequestParam(required = false) Boolean discounted,
            @PageableDefault(size = 9) Pageable pageable,
            @RequestParam(required = false) String success,
            Model model) {

        if (success != null) {
            model.addAttribute("success", success);
        }

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1
                ? Sort.Direction.fromString(sortParams[1])
                : Sort.Direction.ASC;

        Page<ApplianceResponseDTO> page = service.getAppliances(
                category, discounted, sortField, direction, pageable);

        model.addAttribute("page", page);
        model.addAttribute("categories", Category.values());
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedSort", sort);
        model.addAttribute("discounted", discounted);

        return "appliance/appliances-for-client";
    }
}

