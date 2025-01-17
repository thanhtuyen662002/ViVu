package com.vothanhtuyen.vivu_backend.sevices.location;

import java.util.List;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.vothanhtuyen.vivu_backend.dto.LocationResponseDTO;
import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.repositories.LocationsRepository;
import com.vothanhtuyen.vivu_backend.sevices.translation.TranslationService;
import com.vothanhtuyen.vivu_backend.util.Utils;

@Service
public class LocationServiceImpl implements LocationService {
    
    private final LocationsRepository locationsRepository;
    private final TranslationService translationService;
    private final String language = "en";
    private final String tableName = "locations";

    public LocationServiceImpl(LocationsRepository locationsRepository, TranslationService translationService) {
        this.locationsRepository = locationsRepository;
        this.translationService = translationService;
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
                locationResponseDTO.setCreated_at(location.getCreated_at());
                locationResponseDTO.setUpdated_at(location.getUpdated_at());
                locationResponseDTO.setType(location.getType());
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
    public Locations getLocationByName(String name) {
        try {
            return locationsRepository.findByName(name).get();
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
            locationResponseDTO.setCreated_at(location.getCreated_at());
            locationResponseDTO.setUpdated_at(location.getUpdated_at());
            locationResponseDTO.setType(location.getType());
            return locationResponseDTO;
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public void saveLocationByJSONObject(JSONObject location) {
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

            Locations newLocation = new Locations();
            newLocation.setName(nameVi);
            newLocation.setDescription(descriptionVi);
            newLocation.setRegion(regionVi);
            newLocation.setCountry(countryVi);
            newLocation.setCreated_at(Utils.getCurrentTime());
            Locations savedLocation = locationsRepository.save(newLocation);
            Long locationId = savedLocation.getId();

            translationService.insertTranslation(tableName, "name", locationId, language, nameEn);
            translationService.insertTranslation(tableName, "description", locationId, language, descriptionEn);
            translationService.insertTranslation(tableName, "region", locationId, language, regionEn);
            translationService.insertTranslation(tableName, "country", locationId, language, countryEn);
        } catch (Exception e) {
        }
    }

    @Override
    public LocationResponseDTO convertLocationDTOByJSONObject(JSONObject location) {
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

            LocationResponseDTO locationResponseDTO = new LocationResponseDTO();
            locationResponseDTO.setNameVi(nameVi);
            locationResponseDTO.setNameEn(nameEn);
            locationResponseDTO.setDescriptionVi(descriptionVi);
            locationResponseDTO.setDescriptionEn(descriptionEn);
            locationResponseDTO.setRegionVi(regionVi);
            locationResponseDTO.setRegionEn(regionEn);
            locationResponseDTO.setCountryVi(countryVi);
            locationResponseDTO.setCountryEn(countryEn);
            return locationResponseDTO;
        } catch (Exception e) {
        }
        return null;
    }
}
