package com.vothanhtuyen.vivu_backend.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {
    Long id;
    String nameVi;
    String nameEn;
    String addressVi;
    String addressEn;
    Set<String> amenitiesVi;
    Set<String> amenitiesEn;
    String imageUrl;
    Double rating;
    String priceRangeVi;
    String priceRangeEn;
    String type;
}
