package com.vothanhtuyen.vivu_backend.sevices.eatery;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.vothanhtuyen.vivu_backend.dto.EateryResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Eatery;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.repositories.EaterysRepository;
import com.vothanhtuyen.vivu_backend.sevices.image.ImageService;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;
import com.vothanhtuyen.vivu_backend.util.Utils;

@Service
public class EateryServiceImpl implements EateryService {

    private final EaterysRepository eaterysRepository;
    private final TranslationService translationService;
    private final ImageService imageService;
    private final String language = "en";
    private final String tableName = "eaterys";

    public EateryServiceImpl(EaterysRepository eaterysRepository, TranslationService translationService,
            ImageService imageService) {
        this.eaterysRepository = eaterysRepository;
        this.translationService = translationService;
        this.imageService = imageService;
    }

    @Override
    public List<Eatery> getAllEaterysByLocationId(Long locationId) {
        try {
            return eaterysRepository.findAllByLocationsId(locationId).get();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<EateryResponseDTO> getAllEaterysDTOByLocationId(Long locationId) {
        try {
            List<Eatery> eaterys = eaterysRepository.findAllByLocationsId(locationId).get();
            return eaterys.stream().map(eatery -> {
                EateryResponseDTO eateryResponseDTO = new EateryResponseDTO();
                eateryResponseDTO.setId(eatery.getId());
                eateryResponseDTO.setNameVi(eatery.getName());
                eateryResponseDTO
                        .setNameEn(translationService.getTranslation(tableName, "name", eatery.getId(), language));
                eateryResponseDTO.setAddressVi(eatery.getAddress());
                eateryResponseDTO.setAddressEn(
                        translationService.getTranslation(tableName, "address", eatery.getId(), language));
                eateryResponseDTO.setImageUrl(eatery.getImageUrl());
                eateryResponseDTO.setRating(eatery.getRating());
                eateryResponseDTO.setPriceRangeVi(eatery.getPriceRange());
                eateryResponseDTO.setPriceRangeEn(
                        translationService.getTranslation(tableName, "priceRange", eatery.getId(), language));
                eateryResponseDTO.setTypeVi(eatery.getType());
                eateryResponseDTO.setTypeEn(translationService.getTranslation(tableName, "type", eatery.getId(), language));
                return eateryResponseDTO;
            }).toList();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<EateryResponseDTO> convertEaterysByJSONArray(JSONArray eaterys, Locations location) {
        List<EateryResponseDTO> eateryResponseDTOs = new ArrayList<>();
        try {
            for (int i = 0; i < eaterys.length(); i++) {
                JSONObject eatery = eaterys.getJSONObject(i);

                // Get information from JSON
                String nameVi = eatery.getJSONObject("name").getString("vi");
                String nameEn = eatery.getJSONObject("name").getString("en");

                String addressVi = eatery.getJSONObject("address").getString("vi");
                String addressEn = eatery.getJSONObject("address").getString("en");

                String priceRangeVi = eatery.getJSONObject("price_range").getString("vi");
                String priceRangeEn = eatery.getJSONObject("price_range").getString("en");

                double rating = eatery.getDouble("rating");

                String typeVi = Utils.capitalizeFirstLetter(eatery.getString("typeVi"));
                String typeEn = Utils.capitalizeFirstLetter(eatery.getString("typeEn"));

                // Get image URL
                String imageUrl = imageService.getImage(nameVi + "," + location.getName());

                // Save eatery information to database
                Eatery newEatery = new Eatery();
                newEatery.setName(nameVi);
                newEatery.setAddress(addressVi);
                newEatery.setRating(rating);
                newEatery.setPriceRange(priceRangeVi);
                newEatery.setImageUrl(imageUrl);
                newEatery.setLocations(location);
                newEatery.setType(typeVi);
                Eatery savedEatery = eaterysRepository.save(newEatery);
                Long eateryId = savedEatery.getId();

                // Create response DTO
                EateryResponseDTO eateryResponseDTO = new EateryResponseDTO();
                eateryResponseDTO.setId(eateryId);
                eateryResponseDTO.setNameVi(nameVi);
                eateryResponseDTO.setNameEn(nameEn);
                eateryResponseDTO.setAddressVi(addressVi);
                eateryResponseDTO.setAddressEn(addressEn);
                eateryResponseDTO.setRating(rating);
                eateryResponseDTO.setPriceRangeVi(priceRangeVi);
                eateryResponseDTO.setPriceRangeEn(priceRangeEn);
                eateryResponseDTO.setImageUrl(imageUrl);
                eateryResponseDTO.setTypeVi(typeVi);
                eateryResponseDTO.setTypeEn(typeEn);

                // Add DTO to list
                eateryResponseDTOs.add(eateryResponseDTO);

                // Save translations
                saveTranslations(eateryId, nameEn, addressEn, priceRangeEn, typeEn);
            }
            return eateryResponseDTOs;
        } catch (Exception e) {
        }
        return null;
    }

    // Helper method to save translations
    private void saveTranslations(Long eateryId, String nameEn, String addressEn, String priceRangeEn, String type) {
        translationService.insertTranslation(tableName, "name", eateryId, language, nameEn);
        translationService.insertTranslation(tableName, "address", eateryId, language, addressEn);
        translationService.insertTranslation(tableName, "priceRange", eateryId, language, priceRangeEn);
        translationService.insertTranslation(tableName, "type", eateryId, language, type);
    }
}
