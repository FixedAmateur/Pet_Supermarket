package com.laborexport.pet_supermarket.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
@Builder()
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Builder.Default
    private Double totalPrice = 0.0;

    @Builder.Default
    private Long quantity = 0l;

    @Builder.Default
    private boolean purchased = false;

    private LocalDateTime orderUpdated;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<OrderProduct> orderProducts = new HashSet<>();


}
