package com.epam.rd.autocode.assessment.appliances.controller.client;

import com.epam.rd.autocode.assessment.appliances.exceptions.expected.EntityExistsByEmailException;
import com.epam.rd.autocode.assessment.appliances.model.dto.client.ClientEditDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.client.ClientResponseDTO;
import com.epam.rd.autocode.assessment.appliances.model.dto.order.OrderResponseDTO;
import com.epam.rd.autocode.assessment.appliances.service.ClientService;
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
@RequestMapping("/cabinet")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CabinetController {

    private final ClientService clientService;
    private final OrderService orderService;

    private static final String REDIRECT_TO_CABINET = "redirect:/cabinet";
    private static final String CLIENT_RESPONSE_ATTR = "clientResponse";
    private static final String CLIENT_EDIT_ATTR = "clientEdit";
    private static final String ORDERS_LIST_ATTR = "ordersList";
    private static final String EDIT_PAGE = "client/edit";
    private static final String CABINET_PAGE = "client/cabinet";


    // client want to see his cabinet
    @GetMapping
    public String getClientCabinet(Model model, Authentication authentication) {

        Long clientID = Long.parseLong(authentication.getPrincipal().toString());
        ClientResponseDTO clientDTO = clientService.findById(clientID);
        List<OrderResponseDTO> orders = orderService.findByClientId(clientID);
        System.out.println("=====================");
        orders.forEach(System.out::println);
        System.out.println("=====================");

        model.addAttribute(CLIENT_RESPONSE_ATTR, clientDTO);
        model.addAttribute(ORDERS_LIST_ATTR, orders);
        return CABINET_PAGE;
    }

    // client want to see edit page, to edit his data (like name)
    @GetMapping("/edit")
    public String getPageForEditClient(Model model, Authentication authentication) {
        Long clientID = Long.parseLong(authentication.getPrincipal().toString());
        ClientEditDTO toEditDTO = clientService.findByIdToEdit(clientID);
        model.addAttribute(CLIENT_EDIT_ATTR, toEditDTO);
        return EDIT_PAGE;
    }

    // client send edited data to save it in DB
    @PutMapping
    public String processEditClient(@Valid @ModelAttribute(CLIENT_EDIT_ATTR) ClientEditDTO client,
                             BindingResult bindingResult,
                             Authentication authentication) {

        if(bindingResult.hasErrors()) {
            return EDIT_PAGE;
        }
        long clientID = Long.parseLong(authentication.getPrincipal().toString());

        try {
            clientService.update(clientID, client);
        } catch (EntityExistsByEmailException e) {
            bindingResult.rejectValue("email", "validation.email.already-exist");
            return EDIT_PAGE;
        }

        return REDIRECT_TO_CABINET;
    }

//    @ExceptionHandler(NotFoundWhileUpdatingException.class)
//    public String handleEmailAlreadyInUse() {
//        return "error/404";
//    }

}
