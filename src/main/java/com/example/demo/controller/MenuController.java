package com.example.demo.controller;

import com.example.demo.dto.MealRequest;
import com.example.demo.model.Meal;
import com.example.demo.model.MenuItem;
import com.example.demo.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping("/week")
    public ResponseEntity<MenuItem> createWeekMenu(
            @RequestParam LocalDate weekStartDate) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuService.createWeekMenu(weekStartDate));
    }

    @PostMapping("/meals")
    public ResponseEntity<Meal> addMeal(
            @Valid @RequestBody MealRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(menuService.addMeal(request));
    }

    @PutMapping("/meals/{id}")
    public Meal updateMeal(
            @PathVariable Long id,
            @Valid @RequestBody MealRequest request) {

        return menuService.updateMeal(id, request);
    }

    @GetMapping("/meals/date/{date}")
    public List<Meal> getMealsByDate(
            @PathVariable LocalDate date) {

        return menuService.getMealsByDate(date);
    }

    @GetMapping("/meals/week/{weekStartDate}")
    public List<Meal> getMealsByWeek(
            @PathVariable LocalDate weekStartDate) {

        return menuService.getMealsByWeek(weekStartDate);
    }
}
