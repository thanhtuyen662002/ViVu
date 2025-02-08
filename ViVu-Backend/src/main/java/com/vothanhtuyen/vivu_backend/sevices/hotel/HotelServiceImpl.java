package com.vothanhtuyen.vivu_backend.sevices.hotel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.HotelResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Hotels;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.repositories.HotelsRepository;
import com.vothanhtuyen.vivu_backend.sevices.image.ImageService;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;
import com.vothanhtuyen.vivu_backend.util.Utils;

@Service
public class HotelServiceImpl implements HotelService {
    private final HotelsRepository hotelsRepository;
    private final TranslationService translationService;
    private final ImageService imageService;
    private final String language = "en";
    private final String tableName = "hotels";

    public HotelServiceImpl(HotelsRepository hotelsRepository, TranslationService translationService,
            ImageService imageService) {
        this.hotelsRepository = hotelsRepository;
        this.translationService = translationService;
        this.imageService = imageService;
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
            return hotels.stream().map(hotel -> {
                Set<String> amenitiesViSet = new HashSet<>(Arrays.asList(hotel.getAmenities().split(",")));
                Set<String> amenitiesEnSet = new HashSet<>(Arrays.asList(translationService.getTranslation(tableName,
                        "amenities", hotel.getId(), language).split(",")));

                HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
                hotelResponseDTO.setId(hotel.getId());
                hotelResponseDTO.setNameVi(hotel.getName());
                hotelResponseDTO
                        .setNameEn(translationService.getTranslation(tableName, "name", hotel.getId(), language));
                hotelResponseDTO.setAddressVi(hotel.getAddress());
                hotelResponseDTO
                        .setAddressEn(translationService.getTranslation(tableName, "address", hotel.getId(), language));
                hotelResponseDTO.setAmenitiesVi(amenitiesViSet);
                hotelResponseDTO.setAmenitiesEn(amenitiesEnSet);
                hotelResponseDTO.setImageUrl(hotel.getImageUrl());
                hotelResponseDTO.setRating(hotel.getRating());
                hotelResponseDTO.setPriceRangeVi(hotel.getPriceRange());
                hotelResponseDTO.setPriceRangeEn(
                        translationService.getTranslation(tableName, "priceRange", hotel.getId(), language));
                hotelResponseDTO.setTypeVi(hotel.getType());
                hotelResponseDTO.setTypeEn(translationService.getTranslation(tableName, "type", hotel.getId(), language));
                return hotelResponseDTO;
            }).toList();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<HotelResponseDTO> convertHotelsByJSONArray(JSONArray hotels, Locations location) {
        List<HotelResponseDTO> hotelResponseDTOs = new ArrayList<>();
        try {
            for (int i = 0; i < hotels.length(); i++) {
                JSONObject hotel = hotels.getJSONObject(i);

                // Lấy các trường thông tin từ JSON
                String nameVi = hotel.getJSONObject("name").getString("vi");
                String nameEn = hotel.getJSONObject("name").getString("en");

                String addressVi = hotel.getJSONObject("address").getString("vi");
                String addressEn = hotel.getJSONObject("address").getString("en");

                JSONArray amenitiesViArray = hotel.getJSONObject("amenities").getJSONArray("vi");
                JSONArray amenitiesEnArray = hotel.getJSONObject("amenities").getJSONArray("en");

                String priceRangeVi = hotel.getJSONObject("price_range").getString("vi");
                String priceRangeEn = hotel.getJSONObject("price_range").getString("en");

                double rating = hotel.getDouble("rating");

                String typeVi = Utils.capitalizeFirstLetter(hotel.getString("typeVi"));
                String typeEn = Utils.capitalizeFirstLetter(hotel.getString("typeEn"));

                // Gọi dịch vụ để lấy URL ảnh
                String imageUrl = imageService.getImage(nameVi + "," + location.getName());

                // Chuyển JSONArray amenities sang Set<String>
                Set<String> amenitiesViSet = jsonArrayToSet(amenitiesViArray);
                Set<String> amenitiesEnSet = jsonArrayToSet(amenitiesEnArray);

                // Lưu thông tin khách sạn vào database
                Hotels newHotel = new Hotels();
                newHotel.setName(nameVi);
                newHotel.setAddress(addressVi);
                newHotel.setAmenities(String.join(", ", amenitiesViSet));
                newHotel.setRating(rating);
                newHotel.setPriceRange(priceRangeVi);
                newHotel.setImageUrl(imageUrl);
                newHotel.setLocations(location);
                newHotel.setType(typeVi);
                Hotels savedHotel = hotelsRepository.save(newHotel);
                Long hotelId = savedHotel.getId();

                // Tạo DTO phản hồi
                HotelResponseDTO hotelResponseDTO = new HotelResponseDTO();
                hotelResponseDTO.setId(hotelId);
                hotelResponseDTO.setNameVi(nameVi);
                hotelResponseDTO.setNameEn(nameEn);
                hotelResponseDTO.setAddressVi(addressVi);
                hotelResponseDTO.setAddressEn(addressEn);
                hotelResponseDTO.setAmenitiesVi(amenitiesViSet);
                hotelResponseDTO.setAmenitiesEn(amenitiesEnSet);
                hotelResponseDTO.setRating(rating);
                hotelResponseDTO.setPriceRangeVi(priceRangeVi);
                hotelResponseDTO.setPriceRangeEn(priceRangeEn);
                hotelResponseDTO.setImageUrl(imageUrl);
                hotelResponseDTO.setTypeVi(typeVi);
                hotelResponseDTO.setTypeEn(typeEn);

                // Thêm DTO vào danh sách
                hotelResponseDTOs.add(hotelResponseDTO);

                // Lưu các bản dịch
                saveTranslations(hotelId, nameEn, addressEn, amenitiesEnSet, priceRangeEn, typeEn);
            }
        } catch (Exception e) {
            // Log lỗi để dễ dàng debug
            System.err.println("Error while converting hotels: " + e.getMessage());
            e.printStackTrace();
        }
        return hotelResponseDTOs;
    }

    // Hàm chuyển JSONArray thành Set<String>
    private Set<String> jsonArrayToSet(JSONArray jsonArray) {
        Set<String> resultSet = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            resultSet.add(jsonArray.getString(i));
        }
        return resultSet;
    }

    // Hàm lưu bản dịch
    private void saveTranslations(Long hotelId, String nameEn, String addressEn, Set<String> amenitiesEn,
            String priceRangeEn, String type) {
        translationService.insertTranslation("hotels", "name", hotelId, "en", nameEn);
        translationService.insertTranslation("hotels", "address", hotelId, "en", addressEn);
        translationService.insertTranslation("hotels", "amenities", hotelId, "en", String.join(", ", amenitiesEn));
        translationService.insertTranslation("hotels", "priceRange", hotelId, "en", priceRangeEn);
        translationService.insertTranslation("hotels", "type", hotelId, "en", type);
    }

}
