package com.example.demo.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class OptOutRequest {

    @NotNull
    public Long residentId;

    @NotNull
    public LocalDate optOutDate;
}
