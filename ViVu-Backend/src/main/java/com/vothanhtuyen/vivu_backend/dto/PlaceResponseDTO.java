package com.vothanhtuyen.vivu_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceResponseDTO {
    Long id;
    String nameVi;
    String nameEn;
    String descriptionVi;
    String descriptionEn;
    String noteVi;
    String noteEn;
    String imageUrl;
}
