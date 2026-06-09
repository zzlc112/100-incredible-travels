package com.travel.dto;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DtoValidationTest {

    private static Validator validator;
    private static ObjectMapper objectMapper;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        objectMapper = new ObjectMapper();
    }

    @Test
    void travelSaveRequestShouldRejectBlankTitle() {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("");
        req.setSubtitle("sub");
        req.setCoverImage("img");
        req.setExperienceTypes(List.of("extreme"));
        req.setVisualStyle("cinematic");
        req.setRarityLevel(3);
        req.setDestination("dest");
        req.setContent("content");

        Set<ConstraintViolation<TravelSaveRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("title")));
    }

    @Test
    void travelSaveRequestShouldRejectEmptyExperienceTypes() {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("title");
        req.setSubtitle("sub");
        req.setCoverImage("img");
        req.setExperienceTypes(List.of());
        req.setVisualStyle("cinematic");
        req.setRarityLevel(3);
        req.setDestination("dest");
        req.setContent("content");

        Set<ConstraintViolation<TravelSaveRequest>> violations = validator.validate(req);
        assertFalse(violations.isEmpty());
    }

    @Test
    void travelSaveRequestShouldRejectRarityLevelOutOfRange() {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("title");
        req.setSubtitle("sub");
        req.setCoverImage("img");
        req.setExperienceTypes(List.of("extreme"));
        req.setVisualStyle("cinematic");
        req.setRarityLevel(5);
        req.setDestination("dest");
        req.setContent("content");

        Set<ConstraintViolation<TravelSaveRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("rarityLevel")));
    }

    @Test
    void apiResponseSuccessFactory() {
        ApiResponse<String> resp = ApiResponse.success("hello");
        assertEquals(200, resp.getCode());
        assertEquals("hello", resp.getData());
        assertEquals("success", resp.getMessage());
    }

    @Test
    void apiResponseErrorFactory() {
        ApiResponse<Void> resp = ApiResponse.error(404, "not found");
        assertEquals(404, resp.getCode());
        assertNull(resp.getData());
        assertEquals("not found", resp.getMessage());
    }

    @Test
    void pageResultFromIpage() {
        IPage<String> mockPage = new Page<>(2, 10);
        mockPage.setTotal(50);
        mockPage.setRecords(List.of("a", "b", "c"));

        PageResult<String> result = PageResult.from(mockPage);
        assertEquals(50L, result.getTotal());
        assertEquals(2, result.getPage());
        assertEquals(10, result.getSize());
        assertEquals(3, result.getList().size());
    }

    @Test
    void travelListRequestDefaults() {
        TravelListRequest req = new TravelListRequest();
        assertEquals(1, req.getPage());
        assertEquals(20, req.getSize());
    }

    @Test
    void loginRequestShouldRejectBlankUsername() {
        LoginRequest req = new LoginRequest();
        req.setUsername("");
        req.setPassword("pass");

        Set<ConstraintViolation<LoginRequest>> violations = validator.validate(req);
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("username")));
    }
}
