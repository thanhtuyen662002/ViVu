package com.vothanhtuyen.vivu_backend.sevices.data;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.DataResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.sevices.ai.AIService;
import com.vothanhtuyen.vivu_backend.sevices.hotel.HotelService;
import com.vothanhtuyen.vivu_backend.sevices.localFood.LocalFoodService;
import com.vothanhtuyen.vivu_backend.sevices.location.LocationService;
import com.vothanhtuyen.vivu_backend.sevices.place.PlaceService;
import com.vothanhtuyen.vivu_backend.sevices.suggestCalendar.SuggestCalendarService;

@Service
public class DataServiceImpl implements DataService {
    
    private final AIService aiService;
    private final LocationService locationService;
    private final HotelService hotelService;
    private final PlaceService placeService;
    private final LocalFoodService localFoodService;
    private final SuggestCalendarService suggestCalendarService;
    
    public DataServiceImpl(AIService aiService, LocationService locationService,
            HotelService hotelService, PlaceService placeService, LocalFoodService localFoodService,
            SuggestCalendarService suggestCalendarService) {
        this.aiService = aiService;
        this.locationService = locationService;
        this.hotelService = hotelService;
        this.placeService = placeService;
        this.localFoodService = localFoodService;
        this.suggestCalendarService = suggestCalendarService;
    }
    
    @Override
    public DataResponseDTO getData(GetDataRequestDTO request) {
        try {
            Locations location = locationService.getLocationByName(request.getLocation());
            if (location != null) {
                return getDataByLocation(location);
            }
            return getDataByOpenAI(request);
        } catch (Exception e) {
        }
        return null;
    }

    private DataResponseDTO getDataByLocation(Locations location) {
        try {
            DataResponseDTO response = new DataResponseDTO();
            response.setLocation(locationService.convertLocation(location));
            response.setHotels(hotelService.getAllHotelsDTOByLocationId(location.getId()));
            response.setPlaces(placeService.getAllPlacesDTOByLocationId(location.getId()));
            response.setLocalFoods(localFoodService.getAllLocalFoodsDTOByLocationId(location.getId()));
            return response;
        } catch (Exception e) {
        }
        return null;
    }

    private DataResponseDTO getDataByOpenAI(GetDataRequestDTO request) {
        try {
            String aiResponse = aiService.getAIResponse(request);
            JSONObject jsonConvert = new JSONObject(aiResponse);

            JSONObject locations = jsonConvert.getJSONObject("locations");
            JSONArray hotels = jsonConvert.getJSONArray("hotels");
            JSONArray places = jsonConvert.getJSONArray("places");
            JSONArray localFoods = jsonConvert.getJSONArray("local_foods");
            JSONObject suggestedCalendar = jsonConvert.getJSONObject("suggested_calendar");

            Locations location = locationService.saveLocationByJSONObject(locations);

            DataResponseDTO response = new DataResponseDTO();
            response.setLocation(locationService.convertLocation(location));
            response.setHotels(hotelService.convertHotelsByJSONArray(hotels, location));
            response.setPlaces(placeService.convertPlacesByJSONArray(places, location));
            response.setLocalFoods(localFoodService.convertLocalFoodsByJSONArray(localFoods, location));
            response.setSuggestedCalendar(suggestCalendarService.convertSuggestedCalendarByJSONObject(suggestedCalendar));
            
            return response;
        } catch (Exception e) {
        }
        return null;
    }
}
