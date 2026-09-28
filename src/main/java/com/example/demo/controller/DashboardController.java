package com.example.demo.controller;

import com.example.demo.dto.DashboardSummary;
import com.example.demo.dto.RatingSummary;
import com.example.demo.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/headcount")
    public DashboardSummary getHeadcount(
            @RequestParam LocalDate date) {

        return dashboardService.getHeadcount(date);
    }

    @GetMapping("/low-rated")
    public List<RatingSummary> getLowRatedMeals(
            @RequestParam LocalDate weekStartDate) {

        return dashboardService.getLowRatedMeals(weekStartDate);
    }
}