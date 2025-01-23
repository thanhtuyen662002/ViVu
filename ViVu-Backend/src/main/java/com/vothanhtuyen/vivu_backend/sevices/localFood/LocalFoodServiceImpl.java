package com.vothanhtuyen.vivu_backend.sevices.localFood;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.LocalFoodResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.LocalFoods;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.repositories.LocalFoodsRepository;
import com.vothanhtuyen.vivu_backend.sevices.image.ImageService;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;

@Service
public class LocalFoodServiceImpl implements LocalFoodService {
    private final LocalFoodsRepository localFoodsRepository;
    private final TranslationService translationService;
    private final ImageService imageService;
    private final String language = "en";
    private final String tableName = "local_foods";

    public LocalFoodServiceImpl(LocalFoodsRepository localFoodsRepository, TranslationService translationService,
            ImageService imageService) {
        this.localFoodsRepository = localFoodsRepository;
        this.translationService = translationService;
        this.imageService = imageService;
    }

    @Override
    public List<LocalFoods> getAllLocalFoodsByLocationId(Long locationId) {
        try {
            return localFoodsRepository.findAllByLocationsId(locationId).get();
        } catch (Exception e) {
        }
        return null;
    }
    
    @Override
    public List<LocalFoodResponseDTO> getAllLocalFoodsDTOByLocationId(Long locationId) {
        try {
            List<LocalFoods> localFoods = localFoodsRepository.findAllByLocationsId(locationId).get();
            return localFoods.stream().map(localFood -> {
                LocalFoodResponseDTO localFoodResponseDTO = new LocalFoodResponseDTO();
                localFoodResponseDTO.setId(localFood.getId());
                localFoodResponseDTO.setNameVi(localFood.getName());
                localFoodResponseDTO.setNameEn(translationService.getTranslation(tableName, "name", localFood.getId(), language));
                localFoodResponseDTO.setDescriptionVi(localFood.getDescription());
                localFoodResponseDTO.setDescriptionEn(translationService.getTranslation(tableName, "description", localFood.getId(), language));
                localFoodResponseDTO.setIngredientsVi(localFood.getIngredients());
                localFoodResponseDTO.setIngredientsEn(translationService.getTranslation(tableName, "ingredients", localFood.getId(), language));
                localFoodResponseDTO.setPriceRangeVi(localFood.getPriceRange());
                localFoodResponseDTO.setPriceRangeEn(translationService.getTranslation(tableName, "priceRange", localFood.getId(), language));
                localFoodResponseDTO.setImageUrl(localFood.getImageUrl());
                localFoodResponseDTO.setType(localFood.getType());
                return localFoodResponseDTO;
            }).toList();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<LocalFoodResponseDTO> convertLocalFoodsByJSONArray(JSONArray localFoods, Locations location) {
        try {
            List<LocalFoodResponseDTO> localFoodResponseDTOs = new ArrayList<>();
            for (int i = 0; i < localFoods.length(); i++) {
                JSONObject localFood = localFoods.getJSONObject(i);

                JSONObject name = localFood.getJSONObject("name");
                String nameVi = name.getString("vi");
                String nameEn = name.getString("en");

                JSONObject description = localFood.getJSONObject("description");
                String descriptionVi = description.getString("vi");
                String descriptionEn = description.getString("en");

                JSONObject ingredients = localFood.getJSONObject("ingredients");
                String ingredientsVi = ingredients.getString("vi");
                String ingredientsEn = ingredients.getString("en");

                JSONObject priceRange = localFood.getJSONObject("price_range");
                String priceRangeVi = priceRange.getString("vi");
                String priceRangeEn = priceRange.getString("en");

                String type = localFood.getString("type");

                String imageUrl = imageService.getImage(nameVi);

                LocalFoods newLocalFood = new LocalFoods();
                newLocalFood.setName(nameVi);
                newLocalFood.setDescription(descriptionVi);
                newLocalFood.setIngredients(ingredientsVi);
                newLocalFood.setPriceRange(priceRangeVi);
                newLocalFood.setLocations(location);
                newLocalFood.setImageUrl(imageUrl);
                newLocalFood.setType(type);
                LocalFoods saveLocalFood = localFoodsRepository.save(newLocalFood);
                Long localFoodId = saveLocalFood.getId();

                LocalFoodResponseDTO localFoodResponseDTO = new LocalFoodResponseDTO();
                localFoodResponseDTO.setId(localFoodId);
                localFoodResponseDTO.setNameVi(nameVi);
                localFoodResponseDTO.setNameEn(nameEn);
                localFoodResponseDTO.setDescriptionVi(descriptionVi);
                localFoodResponseDTO.setDescriptionEn(descriptionEn);
                localFoodResponseDTO.setIngredientsVi(ingredientsVi);
                localFoodResponseDTO.setIngredientsEn(ingredientsEn);
                localFoodResponseDTO.setPriceRangeVi(priceRangeVi);
                localFoodResponseDTO.setPriceRangeEn(priceRangeEn);
                localFoodResponseDTO.setImageUrl(imageUrl);
                localFoodResponseDTO.setType(type);
                localFoodResponseDTOs.add(localFoodResponseDTO);


                translationService.insertTranslation(tableName, "name", localFoodId, language, nameEn);
                translationService.insertTranslation(tableName, "description", localFoodId, language, descriptionEn);
                translationService.insertTranslation(tableName, "ingredients", localFoodId, language, ingredientsEn);
                translationService.insertTranslation(tableName, "priceRange", localFoodId, language, priceRangeEn);
                translationService.insertTranslation(tableName, "type", localFoodId, language, type);
            }
            return localFoodResponseDTOs;
        } catch (Exception e) {
        }
        return null;
    }
}
