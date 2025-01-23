package com.vothanhtuyen.vivu_backend.sevices.data;

import java.util.List;

import com.vothanhtuyen.vivu_backend.dto.DataResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;
import com.vothanhtuyen.vivu_backend.dto.HotelResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.LocalFoodResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.PlaceResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.SuggestedCalendarResponseDTO;

public interface DataService {
    DataResponseDTO getData(GetDataRequestDTO request);
    List<HotelResponseDTO> getMoreHotelsByLocationId(Long locationId);
    List<LocalFoodResponseDTO> getMoreLocalFoodsByLocationId(Long locationId);
    List<PlaceResponseDTO> getMorePlacesByLocationId(Long locationId);
    SuggestedCalendarResponseDTO getSuggestedCalendar(GetDataRequestDTO request);
}
