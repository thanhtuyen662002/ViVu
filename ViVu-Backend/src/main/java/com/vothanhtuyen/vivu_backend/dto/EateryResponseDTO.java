package com.vothanhtuyen.vivu_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EateryResponseDTO {
    Long id;
    String nameVi;
    String nameEn;
    String addressVi;
    String addressEn;
    String imageUrl;
    Double rating;
    String priceRangeVi;
    String priceRangeEn;
    String type;
}
