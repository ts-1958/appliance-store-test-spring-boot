package com.epam.rd.autocode.assessment.appliances.model.dto.order;

import com.epam.rd.autocode.assessment.appliances.model.self.Client;
import com.epam.rd.autocode.assessment.appliances.model.self.Employee;
import com.epam.rd.autocode.assessment.appliances.model.enums.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private boolean approved;
    private Client client;
    private Employee employee;
    private Set<OrderRowResponseDTO> orderRowSet;
    private OrderStatus status;

    public BigDecimal getTotal(){
        return orderRowSet.stream()
                .map(OrderRowResponseDTO::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public String toString() {
        return "OrdersDTO{" +
                "id=" + id +
                ", approved=" + approved +
                ", client=" + ((client==null) ? "n/a" : client.getName()) +
                ", employee=" + ((employee==null) ? "n/a" : employee.getName()) +
                ", orderRowSet=" + orderRowSet +
                ", status=" + status +
                '}';
    }
}
