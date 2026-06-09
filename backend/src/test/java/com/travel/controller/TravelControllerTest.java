package com.travel.controller;

import com.travel.config.MybatisPlusConfig;
import com.travel.dto.PageResult;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelListRequest;
import com.travel.dto.TravelListResponse;
import com.travel.exception.NotFoundException;
import com.travel.service.TravelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = TravelController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MybatisPlusConfig.class}))
class TravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    @Test
    void listTravelsReturns200() throws Exception {
        PageResult<TravelListResponse> page = new PageResult<>();
        page.setTotal(1L);
        page.setPage(1);
        page.setSize(20);
        TravelListResponse item = new TravelListResponse();
        item.setId(1L);
        item.setTitle("Test Travel");
        page.setList(List.of(item));

        when(travelService.listTravels(any(TravelListRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/travels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.list[0].title").value("Test Travel"));
    }

    @Test
    void listTravelsWithFilterParams() throws Exception {
        PageResult<TravelListResponse> page = new PageResult<>();
        page.setTotal(0L);
        page.setPage(1);
        page.setSize(20);
        page.setList(List.of());

        when(travelService.listTravels(any(TravelListRequest.class))).thenReturn(page);

        mockMvc.perform(get("/api/travels")
                        .param("experienceType", "extreme")
                        .param("rarityLevel", "3"))
                .andExpect(status().isOk());
    }

    @Test
    void getTravelDetailReturns200() throws Exception {
        TravelDetailResponse detail = new TravelDetailResponse();
        detail.setId(1L);
        detail.setTitle("Test");
        detail.setContent("# Hello");

        when(travelService.getTravelDetail(1L)).thenReturn(detail);

        mockMvc.perform(get("/api/travels/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.title").value("Test"));
    }

    @Test
    void getTravelDetailNotFoundReturns404() throws Exception {
        when(travelService.getTravelDetail(999L)).thenThrow(new NotFoundException("旅行内容不存在: id=999"));

        mockMvc.perform(get("/api/travels/999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404));
    }
}
