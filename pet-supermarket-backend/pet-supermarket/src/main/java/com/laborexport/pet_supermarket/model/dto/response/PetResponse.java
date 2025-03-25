package com.laborexport.pet_supermarket.model.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetResponse {
    private String petName;

    private Set<ProductResponse> products;

    private Set<ImageResponse> petImages;
}
