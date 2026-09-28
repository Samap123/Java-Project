package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class FeedbackRequest {

    @NotNull
    public Long residentId;

    @NotNull
    public Long mealId;

    @NotNull
    @Min(1)
    @Max(5)
    public Integer rating;

    public String comment;
}