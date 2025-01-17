package com.vothanhtuyen.vivu_backend.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponseDTO {
    Long id;
    String nameVi;
    String nameEn;
    String descriptionVi;
    String descriptionEn;
    String regionVi;
    String regionEn;
    String countryVi;
    String countryEn;
    Timestamp created_at;
    Timestamp updated_at;
    String type;
}
