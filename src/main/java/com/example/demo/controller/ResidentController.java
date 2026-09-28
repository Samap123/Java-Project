package com.example.demo.controller;

import com.example.demo.dto.OptOutRequest;
import com.example.demo.model.MealOptOut;
import com.example.demo.model.Resident;
import com.example.demo.service.ResidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/residents")
public class ResidentController {

    private final ResidentService residentService;

    public ResidentController(ResidentService residentService) {
        this.residentService = residentService;
    }

    @PostMapping
    public ResponseEntity<Resident> addResident(
            @RequestBody Resident resident) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(residentService.addResident(resident));
    }

    @GetMapping
    public List<Resident> getAllResidents() {
        return residentService.getAllResidents();
    }

    @PutMapping("/{id}")
    public Resident updateResident(
            @PathVariable Long id,
            @RequestBody Resident resident) {

        return residentService.updateResident(id, resident);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(
            @PathVariable Long id) {

        residentService.deleteResident(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/opt-out")
    public ResponseEntity<MealOptOut> optOutMeal(
            @Valid @RequestBody OptOutRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(residentService.optOutMeal(request));
    }
}