package com.laborexport.pet_supermarket.model.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "manufacturers")
@Builder()
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long manufacturerId;

    private String manufacturerName;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private Set<Product> products;
}
