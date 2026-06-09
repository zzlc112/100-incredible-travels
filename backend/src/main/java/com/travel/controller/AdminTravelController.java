package com.travel.controller;

import com.travel.dto.ApiResponse;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelSaveRequest;
import com.travel.service.TravelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminTravelController {

    private final TravelService travelService;

    public AdminTravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping("/travels")
    public ApiResponse<TravelDetailResponse> createTravel(@Valid @RequestBody TravelSaveRequest body) {
        return ApiResponse.success(travelService.createTravel(body));
    }

    @PutMapping("/travels/{id}")
    public ApiResponse<TravelDetailResponse> updateTravel(@PathVariable Long id, @Valid @RequestBody TravelSaveRequest body) {
        return ApiResponse.success(travelService.updateTravel(id, body));
    }

    @DeleteMapping("/travels/{id}")
    public ApiResponse<Void> deleteTravel(@PathVariable Long id) {
        travelService.deleteTravel(id);
        return ApiResponse.success(null);
    }
}
