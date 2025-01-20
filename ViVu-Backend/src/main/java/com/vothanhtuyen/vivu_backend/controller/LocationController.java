package com.vothanhtuyen.vivu_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vothanhtuyen.vivu_backend.entities.Locations;
import com.vothanhtuyen.vivu_backend.sevices.location.LocationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/location")
public class LocationController {
    @Autowired
    private LocationService locationService;
    
    @GetMapping("/name")
    public Locations getMethodName(@RequestParam String name) {
        return locationService.getLocationByName(name);
    }
    
}
