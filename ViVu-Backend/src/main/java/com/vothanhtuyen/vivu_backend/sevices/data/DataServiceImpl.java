package com.vothanhtuyen.vivu_backend.sevices.data;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.config.AIPromptConfig;
import com.vothanhtuyen.vivu_backend.dto.DataResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;
import com.vothanhtuyen.vivu_backend.dto.HotelResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.LocalFoodResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.PlaceResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.SuggestedCalendarResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.sevices.ai.AIService;
import com.vothanhtuyen.vivu_backend.sevices.hotel.HotelService;
import com.vothanhtuyen.vivu_backend.sevices.localFood.LocalFoodService;
import com.vothanhtuyen.vivu_backend.sevices.location.LocationService;
import com.vothanhtuyen.vivu_backend.sevices.place.PlaceService;
import com.vothanhtuyen.vivu_backend.sevices.suggestCalendar.SuggestCalendarService;
import com.vothanhtuyen.vivu_backend.util.VietnameseNormalizer;
import com.vothanhtuyen.vivu_backend.sevices.eatery.EateryService;

@Service
public class DataServiceImpl implements DataService {

    private final AIService aiService;
    private final LocationService locationService;
    private final HotelService hotelService;
    private final PlaceService placeService;
    private final LocalFoodService localFoodService;
    private final SuggestCalendarService suggestCalendarService;
    private final AIPromptConfig aiPromptConfig;
    private final EateryService eateryService;

    public DataServiceImpl(AIService aiService, LocationService locationService,
            HotelService hotelService, PlaceService placeService, LocalFoodService localFoodService,
            SuggestCalendarService suggestCalendarService, AIPromptConfig aiPromptConfig,
            EateryService eateryService) {
        this.aiService = aiService;
        this.locationService = locationService;
        this.hotelService = hotelService;
        this.placeService = placeService;
        this.localFoodService = localFoodService;
        this.suggestCalendarService = suggestCalendarService;
        this.aiPromptConfig = aiPromptConfig;
        this.eateryService = eateryService;
    }

    @Override
    public DataResponseDTO getData(String name) {
        try {
            Locations location = locationService.getLocationByName(name);
            if (location != null) {
                return getDataByLocation(location);
            }
            return getDataByOpenAI(name);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<HotelResponseDTO> getMoreHotelsByLocationId(Long locationId) {
        try {
            Locations location = locationService.getLocationById(locationId);
            String aiResponse = aiService.getAIResponse(location.getName(), aiPromptConfig.getGetMoreHotelsPrompt());
            JSONObject jsonConvert = new JSONObject(aiResponse);
            JSONArray hotels = jsonConvert.getJSONArray("hotels");
            return hotelService.convertHotelsByJSONArray(hotels, null);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<LocalFoodResponseDTO> getMoreLocalFoodsByLocationId(Long locationId) {
        try {
            Locations location = locationService.getLocationById(locationId);
            String aiResponse = aiService.getAIResponse(location.getName(),
                    aiPromptConfig.getGetMoreLocalFoodsPrompt());
            JSONObject jsonConvert = new JSONObject(aiResponse);
            JSONArray hotels = jsonConvert.getJSONArray("local_foods");
            return localFoodService.convertLocalFoodsByJSONArray(hotels, location);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<PlaceResponseDTO> getMorePlacesByLocationId(Long locationId) {
        try {
            Locations location = locationService.getLocationById(locationId);
            String aiResponse = aiService.getAIResponse(location.getName(), aiPromptConfig.getGetMorePlacesPrompt());
            JSONObject jsonConvert = new JSONObject(aiResponse);
            JSONArray hotels = jsonConvert.getJSONArray("local_foods");
            return placeService.convertPlacesByJSONArray(hotels, location);
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public SuggestedCalendarResponseDTO getSuggestedCalendar(GetDataRequestDTO request) {
        try {
            String requestString = request.getLocation() + "," + request.getBudget() + "," + request.getNeeds() + ","
                    + request.getDetails();
            String aiResponse = aiService.getAIResponse(requestString, aiPromptConfig.getGetSuggestCalendarPrompt());
            JSONObject jsonConvert = new JSONObject(aiResponse);
            JSONObject suggestedCalendar = jsonConvert.getJSONObject("suggested_calendar");
            return suggestCalendarService.convertSuggestedCalendarByJSONObject(suggestedCalendar);
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
            response.setEaterys(eateryService.getAllEaterysDTOByLocationId(location.getId()));
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private DataResponseDTO getDataByOpenAI(String request) {
        return getDataByOpenAI(request, 3); // Max 3 retries
    }

    private DataResponseDTO getDataByOpenAI(String request, int retriesLeft) {
        try {
            if (retriesLeft <= 0) {
                return null;
            }

            String aiResponse = aiService.getAIResponse(request, aiPromptConfig.getAiPrompt());
            aiResponse = aiResponse.replace("```json", "");
            if (aiResponse.isEmpty() || aiResponse.isBlank()) {
                return getDataByOpenAI(request, retriesLeft - 1);
            }
            JSONObject jsonConvert = new JSONObject(aiResponse);

            JSONObject locations = jsonConvert.getJSONObject("location");
            JSONObject name = locations.getJSONObject("name");
            String nameVi = name.getString("vi");
            String nameResults = VietnameseNormalizer.normalize(nameVi.trim().toLowerCase());
            if (nameResults.equals("khongxacdinh") || nameResults.equals("chuaxacdinh")) {
                return null;
            }

            JSONArray places = jsonConvert.getJSONArray("places");
            JSONArray hotels = jsonConvert.getJSONArray("hotels");
            JSONArray localFoods = jsonConvert.getJSONArray("local_foods");
            JSONArray eaterys = jsonConvert.getJSONArray("eaterys");

            Locations location = locationService.saveLocationByJSONObject(locations);

            DataResponseDTO response = new DataResponseDTO();
            response.setLocation(locationService.convertLocation(location));
            response.setHotels(hotelService.convertHotelsByJSONArray(hotels, location));
            response.setPlaces(placeService.convertPlacesByJSONArray(places, location));
            response.setLocalFoods(localFoodService.convertLocalFoodsByJSONArray(localFoods, location));
            response.setEaterys(eateryService.convertEaterysByJSONArray(eaterys, location));
            return response;

        } catch (Exception e) {
            // Log the error instead of silently catching it
            System.err.println("Error in getDataByOpenAI: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
