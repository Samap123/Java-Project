package com.example.demo.service;

import com.example.demo.dto.OptOutRequest;
import com.example.demo.model.MealOptOut;
import com.example.demo.model.Resident;
import com.example.demo.repository.MealOptOutRepository;
import com.example.demo.repository.ResidentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {

    private final ResidentRepository residentRepository;
    private final MealOptOutRepository mealOptOutRepository;

    public ResidentService(ResidentRepository residentRepository,
                           MealOptOutRepository mealOptOutRepository) {
        this.residentRepository = residentRepository;
        this.mealOptOutRepository = mealOptOutRepository;
    }

    public Resident addResident(Resident resident) {
        return residentRepository.save(resident);
    }

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Resident getResidentById(Long id) {
        return residentRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Resident not found"));
    }

    public Resident updateResident(Long id, Resident input) {

        Resident resident = getResidentById(id);

        resident.setName(input.getName());
        resident.setRoomNumber(input.getRoomNumber());

        return residentRepository.save(resident);
    }

    public void deleteResident(Long id) {
        Resident resident = getResidentById(id);
        residentRepository.delete(resident);
    }

    public MealOptOut optOutMeal(OptOutRequest request) {

        boolean alreadyOptedOut =
                mealOptOutRepository.existsByResidentIdAndOptOutDate(
                        request.residentId,
                        request.optOutDate
                );

        if (alreadyOptedOut) {
            throw new IllegalArgumentException(
                    "Resident already opted out for this date"
            );
        }

        MealOptOut mealOptOut = new MealOptOut();
        mealOptOut.setResident(getResidentById(request.residentId));
        mealOptOut.setOptOutDate(request.optOutDate);

        return mealOptOutRepository.save(mealOptOut);
    }
}
