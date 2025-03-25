package com.laborexport.pet_supermarket.model.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ManufacturerResponse {
    private Long manufacturerId;

    private String manufacturerName;
}
