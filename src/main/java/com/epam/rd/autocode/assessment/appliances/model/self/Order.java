package com.epam.rd.autocode.assessment.appliances.model.self;

import com.epam.rd.autocode.assessment.appliances.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Entity
public class Order {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean approved;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Employee employee;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderRow> orderRowSet;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", approved=" + approved +
                ", client=" + client.getName() +
                ", employee=" + employee.getName() +
                ", orderRowCount=" + orderRowSet.size() +
                '}';
    }
}


