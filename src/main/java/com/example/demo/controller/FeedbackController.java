package com.example.demo.controller;

import com.example.demo.dto.FeedbackRequest;
import com.example.demo.dto.RatingSummary;
import com.example.demo.model.Feedback;
import com.example.demo.service.FeedbackService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Feedback> submitFeedback(
            @Valid @RequestBody FeedbackRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(feedbackService.submitFeedback(request));
    }

    @GetMapping("/meal/{mealId}/average")
    public RatingSummary getAverageRating(
            @PathVariable Long mealId) {

        return feedbackService.getAverageRating(mealId);
    }
}
