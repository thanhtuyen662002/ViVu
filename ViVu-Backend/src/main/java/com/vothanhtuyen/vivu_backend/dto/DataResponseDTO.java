package com.vothanhtuyen.vivu_backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataResponseDTO {
    LocationResponseDTO location;
    List<HotelResponseDTO> hotels;
    List<PlaceResponseDTO> places;
    List<LocalFoodResponseDTO> localFoods;
    List<EateryResponseDTO> eaterys;
}
