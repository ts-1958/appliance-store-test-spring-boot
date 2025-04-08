package com.epam.rd.autocode.assessment.appliances.controller.internal;

import com.epam.rd.autocode.assessment.appliances.exceptions.expected.EntityExistsByEmailException;
import com.epam.rd.autocode.assessment.appliances.exceptions.rare.NotFoundWhileUpdatingException;
import com.epam.rd.autocode.assessment.appliances.model.dto.employee.EmployeeEditDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.employee.EmployeeResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.order.OrderResponseDTO;
import com.epam.rd.autocode.assessment.appliances.service.EmployeeService;
import com.epam.rd.autocode.assessment.appliances.service.OrderService;
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
@RequestMapping("/internal/cabinet")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class InternalCabinetController {

    private final EmployeeService employeeService;
    private final OrderService orderService;

    private static final String REDIRECT_TO_CABINET = "redirect:/internal/cabinet";
    private static final String CABINET_PAGE = "employee/cabinet";
    private static final String EDIT_BY_EMPLOYEE_PAGE = "employee/edit-by-employee";
//    private static final String EDIT_BY_ADMIN_PAGE= "employee/edit-by-employee";
    private static final String EMPLOYEE_RESPONSE_ATTR = "employeeResponse";
    private static final String EMPLOYEE_EDIT_ATTR = "employeeEdit";
    private static final String ORDERS_LIST_ATTR = "ordersList";



    @GetMapping
    public String getEmployeeCabinet(Model model, Authentication authentication) {
        Long employeeID = Long.parseLong(authentication.getPrincipal().toString());
        EmployeeResponseDTO employee = employeeService.findById(employeeID);
        List<OrderResponseDTO> orders = orderService.findByEmployeeId(employeeID);

        model.addAttribute(EMPLOYEE_RESPONSE_ATTR, employee);
        model.addAttribute(ORDERS_LIST_ATTR, orders);
        return CABINET_PAGE;
    }

    @GetMapping("/edit")
    public String editEmployee(Model model, Authentication authentication) {
        Long employeeID = Long.parseLong(authentication.getPrincipal().toString());

        EmployeeEditDTO dto = employeeService.findByIdToEdit(employeeID);
        model.addAttribute(EMPLOYEE_EDIT_ATTR, dto);

        return EDIT_BY_EMPLOYEE_PAGE;
    }

    @PutMapping
    public String processEdit(@Valid @ModelAttribute(EMPLOYEE_EDIT_ATTR) EmployeeEditDTO employee,
                              BindingResult result,
                              Authentication authentication) {

        if (result.hasErrors()) {
            return EDIT_BY_EMPLOYEE_PAGE;
        }

        long employeeID = Long.parseLong(authentication.getPrincipal().toString());

        try {
            employeeService.update(employee, employeeID);
            return REDIRECT_TO_CABINET;
        } catch (EntityExistsByEmailException e) {
            result.rejectValue("email", "validation.email.already-exist");
            return EDIT_BY_EMPLOYEE_PAGE;
        }
    }

    @ExceptionHandler(NotFoundWhileUpdatingException.class)
    public String handleError() {
        // todo message for employee in Model
        return "error/404";
    }

}
