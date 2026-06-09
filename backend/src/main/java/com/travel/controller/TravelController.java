package com.travel.controller;

import com.travel.dto.ApiResponse;
import com.travel.dto.PageResult;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelListRequest;
import com.travel.dto.TravelListResponse;
import com.travel.service.TravelService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/travels")
    public ApiResponse<PageResult<TravelListResponse>> listTravels(@Valid TravelListRequest params) {
        return ApiResponse.success(travelService.listTravels(params));
    }

    @GetMapping("/travels/{id}")
    public ApiResponse<TravelDetailResponse> getTravelDetail(@PathVariable Long id) {
        return ApiResponse.success(travelService.getTravelDetail(id));
    }
}
