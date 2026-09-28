package com.example.demo.dto;

public record DashboardSummary(
        long totalResidents,
        long optedOutResidents,
        long expectedHeadcount
) {
}