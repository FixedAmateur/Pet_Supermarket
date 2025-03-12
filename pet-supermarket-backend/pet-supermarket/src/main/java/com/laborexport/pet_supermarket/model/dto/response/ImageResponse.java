package com.laborexport.pet_supermarket.model.dto.response;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private Long imgId;
    private String imgUrl;

}
