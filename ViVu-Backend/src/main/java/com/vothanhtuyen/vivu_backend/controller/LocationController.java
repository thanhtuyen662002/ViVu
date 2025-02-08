package com.vothanhtuyen.vivu_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vothanhtuyen.vivu_backend.sevices.location.LocationService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/location")
public class LocationController {
    @Autowired
    private LocationService locationService;
    
    @GetMapping("/")
    public List<String> getLocations() {
        return locationService.getAllNameLocations();
    }
}
