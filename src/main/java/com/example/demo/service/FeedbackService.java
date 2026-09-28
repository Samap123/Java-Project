package com.example.demo.service;

import com.example.demo.dto.FeedbackRequest;
import com.example.demo.dto.RatingSummary;
import com.example.demo.model.Feedback;
import com.example.demo.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ResidentService residentService;
    private final MenuService menuService;

    public FeedbackService(FeedbackRepository feedbackRepository,
                           ResidentService residentService,
                           MenuService menuService) {
        this.feedbackRepository = feedbackRepository;
        this.residentService = residentService;
        this.menuService = menuService;
    }

    public Feedback submitFeedback(FeedbackRequest request) {

        boolean alreadySubmitted =
                feedbackRepository.existsByResidentIdAndMealId(
                        request.residentId,
                        request.mealId
                );

        if (alreadySubmitted) {
            throw new IllegalArgumentException(
                    "Only one feedback is allowed for the same meal"
            );
        }

        Feedback feedback = new Feedback();

        feedback.setResident(
                residentService.getResidentById(request.residentId)
        );

        feedback.setMeal(
                menuService.getMealById(request.mealId)
        );

        feedback.setRating(request.rating);
        feedback.setComment(request.comment);

        return feedbackRepository.save(feedback);
    }

    public RatingSummary getAverageRating(Long mealId) {

        menuService.getMealById(mealId);

        List<Feedback> feedbackList =
                feedbackRepository.findByMealId(mealId);

        double average = feedbackList.stream()
                .mapToInt(Feedback::getRating)
                .average()
                .orElse(0.0);

        average = Math.round(average * 100.0) / 100.0;

        return new RatingSummary(
                mealId,
                average,
                feedbackList.size()
        );
    }
}
