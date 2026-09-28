package com.example.demo.dto;

import com.example.demo.enums.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class MealRequest {

    @NotNull
    public LocalDate weekStartDate;

    @NotNull
    public LocalDate mealDate;

    @NotNull
    public MealType mealType;

    @NotBlank
    public String foodName;
}
