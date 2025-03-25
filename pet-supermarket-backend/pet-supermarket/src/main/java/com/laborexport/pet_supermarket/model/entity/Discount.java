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
@Table(name = "discounts")
@Builder()
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long discountId;

    private Long discountPercentage;

    private Double discountMinus;

    private String discountDesc;

    private LocalDateTime discountExpiry;

    private LocalDateTime discountStarted;

    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Image> banners;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = "prod_discount",
            joinColumns = @JoinColumn(name = "discount_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    public static boolean isDiscountApplicable(Discount discount) {
        return LocalDateTime.now().isAfter(discount.getDiscountStarted()) && LocalDateTime.now().isBefore(discount.getDiscountExpiry());
    }

    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "order_product_id")
    private OrderProduct appliedOrderProduct;
}
