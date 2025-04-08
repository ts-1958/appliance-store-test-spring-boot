package com.epam.rd.autocode.assessment.appliances.controller.internal;

import com.epam.rd.autocode.assessment.appliances.model.dto.employee.EmployeeCreateDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.employee.EmployeeEditDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.employee.EmployeeResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.order.OrderResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.enums.Role;
import com.epam.rd.autocode.assessment.appliances.service.EmployeeService;
import com.epam.rd.autocode.assessment.appliances.service.OrderService;
import com.epam.rd.autocode.assessment.appliances.service.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/internal/employees")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InternalEmployeeController {

    private final EmployeeService employeeService;
    private final OrderService orderService;
    private final UserService userService;

    @GetMapping
    public String showAll(Model model) {
        List<EmployeeResponseDTO> all = employeeService.findAll(0, 100);
        model.addAttribute("employees", all);
        return "employee/list";
    }

    @GetMapping("/new")
    public String showNewEmployeeForm(Model model) {
        model.addAttribute("employee", new EmployeeCreateDTO());
        model.addAttribute("departments", List.of("Development", "QA", "Devops", "Sales"));
        return "employee/new";
    }

    @PostMapping
    public String addEmployee(@Valid @ModelAttribute("employee") EmployeeCreateDTO employee,
                                 BindingResult result) {
        if (result.hasErrors()) {
            return "employee/new";
        }

        if (userService.existsByEmail(employee.getEmail())) {
            result.rejectValue("email", "validation.email.already-exist");
            return "employee/new";
        }
        employeeService.create(employee);

        return "redirect:/internal/employees";
    }


    @GetMapping("/{id}")
    public String showById(Model model, @PathVariable Long id) {
        EmployeeResponseDTO employee = employeeService.findById(id);
        List<OrderResponseDTO> orders = orderService.findByEmployeeId(id);

        model.addAttribute("employee", employee);
        model.addAttribute("employee_orders", orders);
        model.addAttribute("for_admin", true);
        return "employee/cabinet";
    }

    @GetMapping("/{id}/edit")
    public String showEditEmployeeForm(Model model, @PathVariable Long id) {
        EmployeeResponseDTO employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("roles", List.of(Role.ADMIN, Role.MANAGER));

        return "employee/edit-by-admin";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return "redirect:/internal/employees";
    }

    @PutMapping("/{id}")
    public String processEdit(@Valid @ModelAttribute(name = "employee") EmployeeEditDTO employee, BindingResult bindingResult,
                              @PathVariable Long id, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", List.of(Role.ADMIN, Role.MANAGER));
            return "employee/edit-by-admin";
        }

        try {
            employeeService.update(employee, id);
        } catch (EntityExistsException e) {
            bindingResult.rejectValue("email", "error.email", e.getMessage());
            return "employee/edit-by-admin";
        }

        return "redirect:/internal/employees/" + id;
    }
}