package com.example.demo.repository;

import com.example.demo.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {

    Optional<MenuItem> findByWeekStartDate(LocalDate weekStartDate);
}