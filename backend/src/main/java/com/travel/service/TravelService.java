package com.travel.service;

import com.travel.dto.PageResult;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelListRequest;
import com.travel.dto.TravelListResponse;
import com.travel.dto.TravelSaveRequest;

public interface TravelService {

    PageResult<TravelListResponse> listTravels(TravelListRequest req);

    TravelDetailResponse getTravelDetail(Long id);

    TravelDetailResponse createTravel(TravelSaveRequest req);

    TravelDetailResponse updateTravel(Long id, TravelSaveRequest req);

    void deleteTravel(Long id);
}
