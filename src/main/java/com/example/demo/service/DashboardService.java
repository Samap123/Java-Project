package com.example.demo.service;

import com.example.demo.dto.DashboardSummary;
import com.example.demo.dto.RatingSummary;
import com.example.demo.model.Meal;
import com.example.demo.repository.MealOptOutRepository;
import com.example.demo.repository.MealRepository;
import com.example.demo.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DashboardService {

    private final ResidentRepository residentRepository;
    private final MealOptOutRepository mealOptOutRepository;
    private final MealRepository mealRepository;
    private final FeedbackService feedbackService;

    public DashboardService(ResidentRepository residentRepository,
                            MealOptOutRepository mealOptOutRepository,
                            MealRepository mealRepository,
                            FeedbackService feedbackService) {
        this.residentRepository = residentRepository;
        this.mealOptOutRepository = mealOptOutRepository;
        this.mealRepository = mealRepository;
        this.feedbackService = feedbackService;
    }

    public DashboardSummary getHeadcount(LocalDate date) {

        long totalResidents = residentRepository.count();

        long optedOutResidents =
                mealOptOutRepository.countByOptOutDate(date);

        long expectedHeadcount =
                totalResidents - optedOutResidents;

        return new DashboardSummary(
                totalResidents,
                optedOutResidents,
                expectedHeadcount
        );
    }

    public List<RatingSummary> getLowRatedMeals(
            LocalDate weekStartDate) {

        List<Meal> meals =
                mealRepository.findByMenuItemWeekStartDate(
                        weekStartDate
                );

        return meals.stream()
                .map(Meal::getId)
                .map(feedbackService::getAverageRating)
                .filter(summary ->
                        summary.averageRating() > 0
                                && summary.averageRating() < 3)
                .toList();
    }
}
