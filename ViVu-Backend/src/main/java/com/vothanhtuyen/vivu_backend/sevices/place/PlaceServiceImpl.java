package com.vothanhtuyen.vivu_backend.sevices.place;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.PlaceResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.entities.Places;
import com.vothanhtuyen.vivu_backend.repositories.PlacesRepository;
import com.vothanhtuyen.vivu_backend.sevices.image.ImageService;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;

@Service
public class PlaceServiceImpl implements PlaceService {
    private final PlacesRepository placesRepository;
    private final TranslationService translationService;
    private final ImageService imageService;
    private final String language = "en";
    private final String tableName = "places";

    public PlaceServiceImpl(PlacesRepository placesRepository, TranslationService translationService,
            ImageService imageService) {
        this.placesRepository = placesRepository;
        this.translationService = translationService;
        this.imageService = imageService;
    }

    @Override
    public List<Places> getAllPlacesByLocationId(Long locationID) {
        try {
            return placesRepository.findAllByLocationsId(locationID).get();
        } catch (Exception e) {
        }
        return null;
    }
    
    @Override
    public List<PlaceResponseDTO> getAllPlacesDTOByLocationId(Long locationId) {
        try {
            List<Places> places = placesRepository.findAllByLocationsId(locationId).get();
            return places.stream().map(place -> {
                PlaceResponseDTO placeResponseDTO = new PlaceResponseDTO();
                placeResponseDTO.setId(place.getId());
                placeResponseDTO.setNameVi(place.getName());
                placeResponseDTO.setNameEn(translationService.getTranslation(tableName, "name", place.getId(), language));
                placeResponseDTO.setDescriptionVi(place.getDescription());
                placeResponseDTO.setDescriptionEn(translationService.getTranslation(tableName, "description", place.getId(), language));
                placeResponseDTO.setNoteVi(place.getNote());
                placeResponseDTO.setNoteEn(translationService.getTranslation(tableName, "note", place.getId(), language));
                placeResponseDTO.setImageUrl(place.getImageUrl());
                return placeResponseDTO;
            }).toList();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<PlaceResponseDTO> convertPlacesByJSONArray(JSONArray places, Locations location) {
        try {
            List<PlaceResponseDTO> placeResponseDTOs = new ArrayList<>();
            for (int i = 0; i < places.length(); i++){
                JSONObject place = places.getJSONObject(i);

                JSONObject name = place.getJSONObject("name");
                String nameVi = name.getString("vi");
                String nameEn = name.getString("en");
                
                JSONObject description = place.getJSONObject("description");
                String descriptionVi = description.getString("vi");
                String descriptionEn = description.getString("en");
                
                JSONObject note = place.getJSONObject("note");
                String noteVi = note.getString("vi");
                String noteEn = note.getString("en");

                String imageUrl = imageService.getImage(nameVi + "," + location.getName());
                
                Places newPlace = new Places();
                newPlace.setName(nameVi);
                newPlace.setDescription(descriptionVi);
                newPlace.setNote(noteVi);
                newPlace.setImageUrl(imageUrl);
                newPlace.setLocations(location);
                Places savedPlaces = placesRepository.save(newPlace);
                Long placeId = savedPlaces.getId();

                PlaceResponseDTO placeResponseDTO = new PlaceResponseDTO();
                placeResponseDTO.setId(placeId);
                placeResponseDTO.setNameVi(nameVi);
                placeResponseDTO.setNameEn(nameEn);
                placeResponseDTO.setDescriptionVi(descriptionVi);
                placeResponseDTO.setDescriptionEn(descriptionEn);
                placeResponseDTO.setNoteVi(noteVi);
                placeResponseDTO.setNoteEn(noteEn);
                placeResponseDTO.setImageUrl(imageUrl);
                placeResponseDTOs.add(placeResponseDTO);

                translationService.insertTranslation(tableName, "name", placeId, language, nameEn);
                translationService.insertTranslation(tableName, "description", placeId, language, descriptionEn);
                translationService.insertTranslation(tableName, "note", placeId, language, noteEn);
            }
            return placeResponseDTOs;
        } catch (Exception e) {
        }
        return null;
    }
}
