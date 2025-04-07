package com.epam.rd.autocode.assessment.appliances.model.self;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class OrderRow {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Appliance appliance;

    private Long number;
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Override
    public String toString() {
        return "OrderRow{" +
                "id=" + id +
                ", appliance=" + appliance.getName() +
                ", number=" + number +
                ", amount=" + amount +
                ", order=" + order.getId() +
                '}';
    }
}
