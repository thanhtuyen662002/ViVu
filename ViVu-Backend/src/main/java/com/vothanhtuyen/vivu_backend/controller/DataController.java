package com.vothanhtuyen.vivu_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vothanhtuyen.vivu_backend.dto.DataResponseDTO;
import com.vothanhtuyen.vivu_backend.dto.GetDataRequestDTO;
import com.vothanhtuyen.vivu_backend.sevices.data.DataService;



@RestController
@RequestMapping("/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @PostMapping("/get-data")
    public ResponseEntity<DataResponseDTO> getData(@RequestBody GetDataRequestDTO request) {
        try {
            DataResponseDTO response = dataService.getData(request);
            if (response != null) {
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
