package com.epam.rd.autocode.assessment.appliances.controller.internal;

import com.epam.rd.autocode.assessment.appliances.exceptions.expected.EntityExistsByNameException;
import com.epam.rd.autocode.assessment.appliances.exceptions.expected.EntityExistsByPhoneException;
import com.epam.rd.autocode.assessment.appliances.model.dto.manufacturer.ManufacturerDTO;
import com.epam.rd.autocode.assessment.appliances.service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Locale;

@Controller
@RequestMapping("/internal/manufacturers")
@RequiredArgsConstructor
public class InternalManufacturerController {

    private final ManufacturerService manufacturerService;
    private final MessageSource messageSource;
    private static final String REDIRECT_TO_LIST = "redirect:/internal/manufacturers";
    private static final String MANUFACTURER_NEW = "manufacturer/new";
    private static final String MANUFACTURER_LIST = "manufacturer/list";
    private static final String MANUFACTURER_SHOW = "manufacturer/show";
    private static final String MANUFACTURER_ATTR = "manufacturer";
    private static final String CREATE_DTO_ATTR = "createDto";

    @GetMapping
    public String showManufacturers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<ManufacturerDTO> manufacturerPage = manufacturerService.findAll(PageRequest.of(page, size));
        model.addAttribute("manufacturers", manufacturerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", manufacturerPage.getTotalPages());
        model.addAttribute("totalItems", manufacturerPage.getTotalElements());
        return MANUFACTURER_LIST;
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute(CREATE_DTO_ATTR, new ManufacturerDTO());
        return MANUFACTURER_NEW;
    }

    @GetMapping("/{id}")
    public String getManufacturerById(@PathVariable Long id, Model model) {
        ManufacturerDTO dto = manufacturerService.findById(id);

        System.out.println(dto);

        model.addAttribute(MANUFACTURER_ATTR, dto);
        return MANUFACTURER_SHOW;
    }

    @PostMapping
    public String create(
            @Valid @ModelAttribute(CREATE_DTO_ATTR) ManufacturerDTO createDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        if (bindingResult.hasErrors()) {
            return MANUFACTURER_NEW;
        }

        try {
            manufacturerService.create(createDto);
            redirectAttributes.addFlashAttribute(
                    "success", messageSource.getMessage("banner.manufacturer-successfully-added", null, locale));
            return REDIRECT_TO_LIST;
        } catch (EntityExistsByNameException e) {
            bindingResult.rejectValue("name", "validation.name.already-exist");
            return MANUFACTURER_NEW;
        } catch (EntityExistsByPhoneException e){
            bindingResult.rejectValue("phoneNumber", "validation.phone.already-exist");
            return MANUFACTURER_NEW;
        }
    }




    @PutMapping("/{id}/update")
    public String update(
            @PathVariable("id") Long manufacturerId,
            @Valid @ModelAttribute(MANUFACTURER_ATTR) ManufacturerDTO manufacturerDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Locale locale) {

        if (bindingResult.hasErrors()) {
            return MANUFACTURER_SHOW;
        }

        try {
            manufacturerService.update(manufacturerId, manufacturerDto);
            redirectAttributes.addFlashAttribute(
                    "success", messageSource.getMessage("banner.manufacturer-successfully-updated", null, locale));
            return REDIRECT_TO_LIST;
        } catch (EntityExistsByNameException e) {
            bindingResult.rejectValue("name", "validation.name.already-exist");
            return MANUFACTURER_SHOW;
        } catch (EntityExistsByPhoneException e){
            bindingResult.rejectValue("phoneNumber", "validation.phone.already-exist");
            return MANUFACTURER_SHOW;
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteManufacturer(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        manufacturerService.deleteById(id);
        redirectAttributes.addFlashAttribute("success", "Manufacturer successfully deleted");
        return REDIRECT_TO_LIST;
    }
}

//@Controller
//@RequestMapping("/internal/manufacturers")
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class InternalManufacturerController {
//
//    private final ManufacturerService service;
//
//    @GetMapping
//    public String showAllManufacturers(@RequestParam(defaultValue = "0") int page,
//                                       @RequestParam(defaultValue = "10") int size,
//                                       Model model) {
//        Page<ManufacturerDTO> manufacturerPage = service.findAll(PageRequest.of(page, size));
//        model.addAttribute("manufacturers", manufacturerPage.getContent());
//        model.addAttribute("currentPage", page);
//        model.addAttribute("totalPages", manufacturerPage.getTotalPages());
//        model.addAttribute("totalItems", manufacturerPage.getTotalElements());
//        return "manufacture/manufacturers";
//    }
//
//    @GetMapping("/new")
//    public String showForm(Model model) {
//        model.addAttribute("createDto", new ManufacturerDTO());
//        return "manufacture/newManufacturer";
//    }
//
//    @GetMapping("/{id}")
//    public String getById(Model model, @PathVariable Long id) {
//        ManufacturerDTO manufacturer = service.findById(id);
//        model.addAttribute("manufacturer", manufacturer);
//        return "manufacture/manufacturer";
//    }
//
//    @PostMapping("/new")
//    public String processForm(@Valid @ModelAttribute("createDto") ManufacturerDTO createDto,
//                              BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
//
//        if (bindingResult.hasErrors()) {
//            return "manufacture/newManufacturer";
//        }
//        try {
//            service.register(createDto);
//            redirectAttributes.addFlashAttribute("success", "Successfully registered");
//            return "redirect:/internal/manufacturers";
//        } catch (EntityExistsException e) {
//            bindingResult.rejectValue("name", "manufacturer.exists", e.getMessage());
//        } catch (IllegalArgumentException e) {
//            model.addAttribute("error", e.getMessage());
//        }
//
//        return "manufacture/newManufacturer";
//    }
//
//
//    @PutMapping("/{id}")
//    public String updateManufacturer(
//            @PathVariable Long id,
//            @Valid @ModelAttribute(name = "manufacturer") ManufacturerDTO manufacturerDTO,
//            BindingResult bindingResult,
//            RedirectAttributes redirectAttributes) {
//
//        if (bindingResult.hasErrors()) {
//            System.err.println("MEMEMEM");
//            return "manufacture/manufacturer";
//        }
//
//        service.update(id, manufacturerDTO.getDescription(), manufacturerDTO.getPhoneNumber());
//        redirectAttributes.addFlashAttribute("success", "Successfully updated");
//        return "redirect:/internal/manufacturers";
//    }
//
//    @DeleteMapping("/{id}")
//    public String deleteManufacturer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
//        service.deleteById(id);
//        redirectAttributes.addFlashAttribute("success", "Deleted updated");
//        return "redirect:/internal/manufacturers";
//    }
//}