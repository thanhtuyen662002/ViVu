package com.vothanhtuyen.vivu_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalFoodResponseDTO {
    Long id;
    String nameVi;
    String nameEn;
    String descriptionVi;
    String descriptionEn;
    String imageUrl;
    String ingredientsVi;
    String ingredientsEn;
    String priceRangeVi;
    String priceRangeEn;
    String typeVi;
    String typeEn;
}
