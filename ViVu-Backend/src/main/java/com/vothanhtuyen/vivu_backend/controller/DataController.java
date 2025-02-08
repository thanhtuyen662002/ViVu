package com.vothanhtuyen.vivu_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vothanhtuyen.vivu_backend.dto.DataResponseDTO;
import com.vothanhtuyen.vivu_backend.sevices.data.DataService;



@RestController
@RequestMapping("/api/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping("/get-data")
    public ResponseEntity<DataResponseDTO> getData(@RequestParam String location) {
        try {
            DataResponseDTO response = dataService.getData(location);
            if (response != null) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
