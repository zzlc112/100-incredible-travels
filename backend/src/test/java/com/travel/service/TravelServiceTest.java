package com.travel.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelListRequest;
import com.travel.dto.TravelListResponse;
import com.travel.dto.TravelSaveRequest;
import com.travel.entity.Travel;
import com.travel.exception.NotFoundException;
import com.travel.mapper.TravelMapper;
import com.travel.service.impl.TravelServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TravelServiceTest {

    @Mock
    private TravelMapper travelMapper;

    private TravelServiceImpl service;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        service = new TravelServiceImpl(travelMapper, objectMapper);
    }

    private Travel createEntity(Long id, String title) {
        Travel t = new Travel();
        t.setId(id);
        t.setTitle(title);
        t.setSubtitle("subtitle");
        t.setCoverImage("http://img.jpg");
        t.setDetailImages("[\"http://img1.jpg\"]");
        t.setExperienceTypes("[\"extreme\",\"hidden\"]");
        t.setVisualStyle("cinematic");
        t.setRarityLevel(3);
        t.setDestination("Iceland");
        t.setContent("# Content");
        t.setHighlights("[\"highlight1\"]");
        t.setCreatedAt(LocalDateTime.now());
        t.setUpdatedAt(LocalDateTime.now());
        return t;
    }

    private TravelSaveRequest createSaveRequest() {
        TravelSaveRequest req = new TravelSaveRequest();
        req.setTitle("New Travel");
        req.setSubtitle("subtitle");
        req.setCoverImage("http://img.jpg");
        req.setDetailImages(List.of("http://img1.jpg"));
        req.setExperienceTypes(List.of("extreme"));
        req.setVisualStyle("cinematic");
        req.setRarityLevel(3);
        req.setDestination("Iceland");
        req.setContent("# Content");
        req.setHighlights(List.of("highlight1"));
        return req;
    }

    // --- listTravels ---

    @Test
    void listTravelsReturnsPageResult() {
        TravelListRequest req = new TravelListRequest();
        req.setPage(1);
        req.setSize(10);

        IPage<Travel> mockPage = new Page<>(1, 10);
        mockPage.setRecords(List.of(createEntity(1L, "Test")));
        mockPage.setTotal(1);

        when(travelMapper.selectPageWithFilter(any(), any(), any(), any())).thenReturn(mockPage);

        var result = service.listTravels(req);
        assertEquals(1L, result.getTotal());
        assertEquals(1, result.getList().size());
        assertEquals("Test", result.getList().get(0).getTitle());
    }

    @Test
    void listTravelsEmpty() {
        TravelListRequest req = new TravelListRequest();
        IPage<Travel> mockPage = new Page<>(1, 10);
        mockPage.setRecords(List.of());
        mockPage.setTotal(0);

        when(travelMapper.selectPageWithFilter(any(), any(), any(), any())).thenReturn(mockPage);

        var result = service.listTravels(req);
        assertEquals(0L, result.getTotal());
        assertTrue(result.getList().isEmpty());
    }

    // --- getTravelDetail ---

    @Test
    void getTravelDetailFound() {
        when(travelMapper.selectById(1L)).thenReturn(createEntity(1L, "Test"));

        TravelDetailResponse result = service.getTravelDetail(1L);
        assertEquals(1L, result.getId());
        assertEquals("Test", result.getTitle());
        assertEquals("cinematic", result.getVisualStyle());
    }

    @Test
    void getTravelDetailNotFound() {
        when(travelMapper.selectById(999L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> service.getTravelDetail(999L));
    }

    // --- createTravel ---

    @Test
    void createTravel() {
        TravelSaveRequest req = createSaveRequest();
        Travel entity = createEntity(10L, "New Travel");

        when(travelMapper.insert(any(Travel.class))).thenAnswer(inv -> {
            Travel t = inv.getArgument(0);
            t.setId(10L);
            return 1;
        });
        when(travelMapper.selectById(10L)).thenReturn(entity);

        TravelDetailResponse result = service.createTravel(req);
        assertEquals(10L, result.getId());
        assertEquals("New Travel", result.getTitle());
    }

    // --- updateTravel ---

    @Test
    void updateTravel() {
        TravelSaveRequest req = createSaveRequest();
        req.setTitle("Updated Title");
        Travel existing = createEntity(1L, "Old Title");

        when(travelMapper.selectById(1L)).thenReturn(existing);
        when(travelMapper.updateById(any(Travel.class))).thenReturn(1);
        when(travelMapper.selectById(1L)).thenReturn(existing);

        TravelDetailResponse result = service.updateTravel(1L, req);
        assertEquals("Updated Title", result.getTitle());
    }

    @Test
    void updateTravelNotFound() {
        when(travelMapper.selectById(999L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> service.updateTravel(999L, createSaveRequest()));
    }

    // --- deleteTravel ---

    @Test
    void deleteTravel() {
        when(travelMapper.selectById(1L)).thenReturn(createEntity(1L, "Test"));
        when(travelMapper.deleteById(1L)).thenReturn(1);

        assertDoesNotThrow(() -> service.deleteTravel(1L));
        verify(travelMapper).deleteById(1L);
    }

    @Test
    void deleteTravelNotFound() {
        when(travelMapper.selectById(999L)).thenReturn(null);

        assertThrows(NotFoundException.class, () -> service.deleteTravel(999L));
    }

    // --- JSON helpers (via public API) ---

    @Test
    void toListResponseParsesJsonArrays() {
        Travel entity = createEntity(1L, "Test");
        entity.setExperienceTypes("[\"extreme\",\"hidden\"]");
        entity.setHighlights("[\"h1\",\"h2\"]");

        when(travelMapper.selectById(1L)).thenReturn(entity);

        TravelDetailResponse resp = service.getTravelDetail(1L);
        assertEquals(2, resp.getExperienceTypes().size());
        assertTrue(resp.getExperienceTypes().contains("extreme"));
    }

    @Test
    void parseJsonArrayNullReturnsEmpty() {
        Travel entity = createEntity(1L, "Test");
        entity.setExperienceTypes(null);

        when(travelMapper.selectById(1L)).thenReturn(entity);

        TravelDetailResponse resp = service.getTravelDetail(1L);
        assertNotNull(resp.getExperienceTypes());
        assertTrue(resp.getExperienceTypes().isEmpty());
    }
}
