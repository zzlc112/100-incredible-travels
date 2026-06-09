package com.travel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.config.MybatisPlusConfig;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelSaveRequest;
import com.travel.exception.NotFoundException;
import com.travel.service.TravelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AdminTravelController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {MybatisPlusConfig.class}))
class AdminTravelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TravelService travelService;

    @Autowired
    private ObjectMapper objectMapper;

    private MockHttpSession session;

    @BeforeEach
    void setUp() {
        session = new MockHttpSession();
        session.setAttribute("admin", "admin");
    }

    @Test
    void createTravelReturns200() throws Exception {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("New");
        req.setSubtitle("sub");
        req.setCoverImage("img");
        req.setExperienceTypes(List.of("extreme"));
        req.setVisualStyle("cinematic");
        req.setRarityLevel(3);
        req.setDestination("dest");
        req.setContent("content");

        TravelDetailResponse resp = new TravelDetailResponse();
        resp.setId(1L);
        resp.setTitle("New");

        when(travelService.createTravel(any(TravelSaveRequest.class))).thenReturn(resp);

        mockMvc.perform(post("/api/admin/travels")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(1));
    }

    @Test
    void createTravelMissingRequiredFieldsReturns400() throws Exception {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("");

        mockMvc.perform(post("/api/admin/travels")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(400));
    }

    @Test
    void updateTravelNotFoundReturns404() throws Exception {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("Updated");
        req.setSubtitle("sub");
        req.setCoverImage("img");
        req.setExperienceTypes(List.of("extreme"));
        req.setVisualStyle("cinematic");
        req.setRarityLevel(3);
        req.setDestination("dest");
        req.setContent("content");

        when(travelService.updateTravel(eq(999L), any(TravelSaveRequest.class)))
                .thenThrow(new NotFoundException("旅行内容不存在: id=999"));

        mockMvc.perform(put("/api/admin/travels/999")
                        .session(session)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404));
    }

    @Test
    void deleteTravelNotFoundReturns404() throws Exception {
        org.mockito.Mockito.doThrow(new NotFoundException("旅行内容不存在: id=999")).when(travelService).deleteTravel(999L);

        mockMvc.perform(delete("/api/admin/travels/999")
                        .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(404));
    }
}
