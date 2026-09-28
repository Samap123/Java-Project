package com.example.demo.dto;

public record RatingSummary(
        Long mealId,
        double averageRating,
        long feedbackCount
) {
}
