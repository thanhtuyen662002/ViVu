package com.vothanhtuyen.vivu_backend.sevices.hotel;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.HotelResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Hotels;
import com.vothanhtuyen.vivu_backend.repositories.HotelsRepository;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;

@Service
public class HotelServiceImpl implements HotelService{
    private final HotelsRepository hotelsRepository;
    private final TranslationService translationService;
    private final String language = "en";
    private final String tableName = "hotels";

    public HotelServiceImpl(HotelsRepository hotelsRepository, TranslationService translationService) {
        this.hotelsRepository = hotelsRepository;
        this.translationService = translationService;
    }

    @Override
    public List<Hotels> getAllHotelsByLocationId(Long locationId) {
        try {
            return hotelsRepository.findAllByLocationsId(locationId).get();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<HotelResponseDTO> getAllHotelsDTOByLocationId(Long locationId) {
        try {
            List<Hotels> hotels = hotelsRepository.findAllByLocationsId(locationId).get();
            return hotels.stream().map(hotel ->{
                HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
                hotelResponseDTO.setId(hotel.getId());
                hotelResponseDTO.setNameVi(hotel.getName());
                hotelResponseDTO.setNameEn(translationService.getTranslation(tableName, "name", hotel.getId(), language));
                hotelResponseDTO.setAddressVi(hotel.getAddress());
                hotelResponseDTO.setAddressEn(translationService.getTranslation(tableName, "address", hotel.getId(), language));
                hotelResponseDTO.setAmenitiesVi(hotel.getAmenities());
                hotelResponseDTO.setAmenitiesEn(translationService.getTranslation(tableName, "amenities", hotel.getId(), language));
                hotelResponseDTO.setContactInfo(hotel.getContactInfo());
                hotelResponseDTO.setImageUrl(hotel.getImageUrl());
                hotelResponseDTO.setRating(hotel.getRating());
                hotelResponseDTO.setPriceRangeVi(hotel.getPriceRange());
                hotelResponseDTO.setPriceRangeEn(translationService.getTranslation(tableName, "priceRange", hotel.getId(), language));
                return hotelResponseDTO;
            }).toList();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<HotelResponseDTO> saveHotelsByJSONArray(JSONArray hotels) {
        try {
            for (int i = 0; i < hotels.length(); i++){
                JSONObject hotel = hotels.getJSONObject(i);

                JSONObject name = hotel.getJSONObject("name");
                String nameVi = name.getString("vi");
                String nameEn = name.getString("en");
                
                JSONObject address = hotel.getJSONObject("address");
                String addressVi = address.getString("vi");
                String addressEn = address.getString("en");
                
                JSONObject amenities = hotel.getJSONObject("amenities");
                JSONArray amenitiesVi = amenities.getJSONArray("vi");
                JSONArray amenitiesEn = amenities.getJSONArray("en");
                
                JSONObject priceRange = hotel.getJSONObject("price_range");
                String priceRangeVi = priceRange.getString("vi");
                String priceRangeEn = priceRange.getString("en");

                String contactInfo = hotel.getString("contact_info");

                double rating = hotel.getDouble("rating");

                Hotels newHotels = new Hotels();
                newHotels.setName(nameVi);
                newHotels.setAddress(addressVi);
                newHotels.setAmenities(amenitiesVi.toString());
                newHotels.setContactInfo(contactInfo);
                newHotels.setRating(rating);
                newHotels.setPriceRange(priceRangeVi);
                Hotels savedHotels = hotelsRepository.save(newHotels);
                Long hotelId = savedHotels.getId();

                translationService.insertTranslation(tableName, "name", hotelId, language, nameEn);
                translationService.insertTranslation(tableName, "address", hotelId, language, addressEn);
                translationService.insertTranslation(tableName, "amenities", hotelId, language, amenitiesEn.toString());
                translationService.insertTranslation(tableName, "priceRange", hotelId, language, priceRangeEn);
            }
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<HotelResponseDTO> convertHotelsByJSONArray(JSONArray hotels) {
        try {
            List<HotelResponseDTO> hotelResponseDTOs = new ArrayList<>();
            for (int i = 0; i < hotels.length(); i++){
                JSONObject hotel = hotels.getJSONObject(i);

                JSONObject name = hotel.getJSONObject("name");
                String nameVi = name.getString("vi");
                String nameEn = name.getString("en");
                
                JSONObject address = hotel.getJSONObject("address");
                String addressVi = address.getString("vi");
                String addressEn = address.getString("en");
                
                JSONObject amenities = hotel.getJSONObject("amenities");
                JSONArray amenitiesVi = amenities.getJSONArray("vi");
                JSONArray amenitiesEn = amenities.getJSONArray("en");
                
                JSONObject priceRange = hotel.getJSONObject("price_range");
                String priceRangeVi = priceRange.getString("vi");
                String priceRangeEn = priceRange.getString("en");

                String contactInfo = hotel.getString("contact_info");

                double rating = hotel.getDouble("rating");

                HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
                hotelResponseDTO.setNameVi(nameVi);
                hotelResponseDTO.setNameEn(nameEn);
                hotelResponseDTO.setAddressVi(addressVi);
                hotelResponseDTO.setAddressEn(addressEn);
                hotelResponseDTO.setAmenitiesVi(amenitiesVi.toString());
                hotelResponseDTO.setAmenitiesEn(amenitiesEn.toString());
                hotelResponseDTO.setContactInfo(contactInfo);
                hotelResponseDTO.setRating(rating);
                hotelResponseDTO.setPriceRangeVi(priceRangeVi);
                hotelResponseDTO.setPriceRangeEn(priceRangeEn);
                hotelResponseDTOs.add(hotelResponseDTO);
            }
            return hotelResponseDTOs;
        } catch (Exception e) {
        }
        return null;
    }
}
