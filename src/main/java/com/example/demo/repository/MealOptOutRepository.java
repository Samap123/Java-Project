package com.example.demo.repository;

import com.example.demo.model.MealOptOut;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface MealOptOutRepository extends JpaRepository<MealOptOut, Long> {

    boolean existsByResidentIdAndOptOutDate(
            Long residentId,
            LocalDate optOutDate
    );

    long countByOptOutDate(LocalDate optOutDate);
}
