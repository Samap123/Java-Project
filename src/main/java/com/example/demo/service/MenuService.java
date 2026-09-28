package com.example.demo.service;

import com.example.demo.dto.MealRequest;
import com.example.demo.model.Meal;
import com.example.demo.model.MenuItem;
import com.example.demo.repository.MealRepository;
import com.example.demo.repository.MenuItemRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MenuService {

    private final MenuItemRepository menuItemRepository;
    private final MealRepository mealRepository;

    public MenuService(MenuItemRepository menuItemRepository,
                       MealRepository mealRepository) {
        this.menuItemRepository = menuItemRepository;
        this.mealRepository = mealRepository;
    }

    public MenuItem createWeekMenu(LocalDate weekStartDate) {

        if (menuItemRepository.findByWeekStartDate(weekStartDate).isPresent()) {
            throw new IllegalArgumentException(
                    "Menu already exists for this week"
            );
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setWeekStartDate(weekStartDate);

        return menuItemRepository.save(menuItem);
    }

    public Meal addMeal(MealRequest request) {

        MenuItem menuItem = menuItemRepository
                .findByWeekStartDate(request.weekStartDate)
                .orElseGet(() -> {
                    MenuItem newMenu = new MenuItem();
                    newMenu.setWeekStartDate(request.weekStartDate);
                    return menuItemRepository.save(newMenu);
                });

        Meal meal = new Meal();
        meal.setMenuItem(menuItem);
        meal.setMealDate(request.mealDate);
        meal.setMealType(request.mealType);
        meal.setFoodName(request.foodName);

        return mealRepository.save(meal);
    }

    public Meal getMealById(Long id) {
        return mealRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Meal not found")
                );
    }

    public List<Meal> getMealsByDate(LocalDate date) {
        return mealRepository.findByMealDate(date);
    }

    public List<Meal> getMealsByWeek(LocalDate weekStartDate) {
        return mealRepository.findByMenuItemWeekStartDate(weekStartDate);
    }

    public Meal updateMeal(Long id, MealRequest request) {

        Meal meal = getMealById(id);

        meal.setMealDate(request.mealDate);
        meal.setMealType(request.mealType);
        meal.setFoodName(request.foodName);

        return mealRepository.save(meal);
    }
}
