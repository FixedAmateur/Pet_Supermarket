package com.laborexport.pet_supermarket.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_prod")
@Builder()
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductId;

    private Long subQuantity;

    private Double subPrice;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH})
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH}
    )
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "appliedOrderProduct", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Discount> appliedDiscounts;

    public static OrderProduct updateSubPriceAndDiscount(OrderProduct orderProduct) {
        Double currentSubPrice = orderProduct.getProduct().getUnitPrice() * (double)orderProduct.getSubQuantity();
        Set<Discount> appliedDiscounts = new HashSet<>();
        for (Discount discount : orderProduct.getProduct().getDiscounts()) {
            if (Discount.isDiscountApplicable(discount)) {
                currentSubPrice = currentSubPrice * (discount.getDiscountPercentage()/100) - discount.getDiscountMinus();
                appliedDiscounts.add(discount);
            }
        }
        orderProduct.setSubPrice(currentSubPrice);
        orderProduct.setAppliedDiscounts(appliedDiscounts);
        return orderProduct;
    }


}