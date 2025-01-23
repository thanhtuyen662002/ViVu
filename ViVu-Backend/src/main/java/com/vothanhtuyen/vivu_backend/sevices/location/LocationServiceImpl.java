package com.vothanhtuyen.vivu_backend.sevices.location;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.LocationResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.repositories.LocationsRepository;
import com.vothanhtuyen.vivu_backend.sevices.image.ImageService;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;
import com.vothanhtuyen.vivu_backend.util.Utils;

@Service
public class LocationServiceImpl implements LocationService {
    
    private final LocationsRepository locationsRepository;
    private final TranslationService translationService;
    private final ImageService imageService;
    private final String language = "en";
    private final String tableName = "locations";

    public LocationServiceImpl(LocationsRepository locationsRepository, TranslationService translationService,
            ImageService imageService) {
        this.locationsRepository = locationsRepository;
        this.translationService = translationService;
        this.imageService = imageService;
    }

    @Override
    public List<LocationResponseDTO> getAllLocations() {
        try {
            List<Locations> locations = locationsRepository.findAll();
            return locations.stream().map(location -> {
                LocationResponseDTO locationResponseDTO = new LocationResponseDTO();
                locationResponseDTO.setId(location.getId());
                locationResponseDTO.setNameVi(location.getName());
                locationResponseDTO.setNameEn(translationService.getTranslation(tableName, "name", location.getId(), language));
                locationResponseDTO.setDescriptionVi(location.getDescription());
                locationResponseDTO.setDescriptionEn(translationService.getTranslation(tableName, "description", location.getId(), language));
                locationResponseDTO.setRegionVi(location.getRegion());
                locationResponseDTO.setRegionEn(translationService.getTranslation(tableName, "region", location.getId(), language));
                locationResponseDTO.setCountryVi(location.getCountry());
                locationResponseDTO.setCountryEn(translationService.getTranslation(tableName, "country", location.getId(), language));
                locationResponseDTO.setImageUrl(location.getImageUrl());
                locationResponseDTO.setCreated_at(location.getCreated_at());
                return locationResponseDTO;
            }).toList();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public List<String> getAllNameLocations() {
        try {
            List<LocationResponseDTO> locationResponseDTOs = getAllLocations();
            List<String> names = null;
            for (LocationResponseDTO locationResponseDTO : locationResponseDTOs) {
                names = List.of(locationResponseDTO.getNameVi(), locationResponseDTO.getNameEn());
            }
            return names;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Locations getLocationById(Long id) {
        try {
            return locationsRepository.findById(id).get();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Locations getLocationByName(String name) {
        try {
            String normalizedName = name.trim();

            Locations location = locationsRepository.findByNameIgnoreCase(normalizedName).get();
            if (location != null) {
                return location;
            }

            Long locationId = translationService.getLocationIdByName(normalizedName);
            if (locationId != null) {
                return locationsRepository.findById(locationId).get();
            }
            
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public LocationResponseDTO convertLocation (Locations location){
        try {
            LocationResponseDTO locationResponseDTO = new LocationResponseDTO();
            locationResponseDTO.setId(location.getId());
            locationResponseDTO.setNameVi(location.getName());
            locationResponseDTO.setNameEn(translationService.getTranslation(tableName, "name", location.getId(), language));
            locationResponseDTO.setDescriptionVi(location.getDescription());
            locationResponseDTO.setDescriptionEn(translationService.getTranslation(tableName, "description", location.getId(), language));
            locationResponseDTO.setRegionVi(location.getRegion());
            locationResponseDTO.setRegionEn(translationService.getTranslation(tableName, "region", location.getId(), language));
            locationResponseDTO.setCountryVi(location.getCountry());
            locationResponseDTO.setCountryEn(translationService.getTranslation(tableName, "country", location.getId(), language));
            locationResponseDTO.setImageUrl(location.getImageUrl());
            locationResponseDTO.setCreated_at(location.getCreated_at());
            return locationResponseDTO;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public Locations saveLocationByJSONObject(JSONObject location) {
        try {
            JSONObject name = location.getJSONObject("name");
            String nameVi = name.getString("vi");
            String nameEn = name.getString("en");

            JSONObject description = location.getJSONObject("description");
            String descriptionVi = description.getString("vi");
            String descriptionEn = description.getString("en");

            JSONObject region = location.getJSONObject("region");
            String regionVi = region.getString("vi");
            String regionEn = region.getString("en");

            JSONObject country = location.getJSONObject("country");
            String countryVi = country.getString("vi");
            String countryEn = country.getString("en");

            String imageUrl = imageService.getImage(nameVi);

            Locations newLocation = new Locations();
            newLocation.setName(nameVi);
            newLocation.setDescription(descriptionVi);
            newLocation.setRegion(regionVi);
            newLocation.setCountry(countryVi);
            newLocation.setImageUrl(imageUrl);
            newLocation.setCreated_at(Utils.getCurrentTime());
            Locations savedLocation = locationsRepository.save(newLocation);
            Long locationId = savedLocation.getId();

            translationService.insertTranslation(tableName, "name", locationId, language, nameEn);
            translationService.insertTranslation(tableName, "description", locationId, language, descriptionEn);
            translationService.insertTranslation(tableName, "region", locationId, language, regionEn);
            translationService.insertTranslation(tableName, "country", locationId, language, countryEn);

            return savedLocation;
        } catch (Exception e) {
        }
        return null;
    }
}
