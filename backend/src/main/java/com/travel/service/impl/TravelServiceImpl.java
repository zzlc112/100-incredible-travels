package com.travel.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.dto.PageResult;
import com.travel.dto.TravelDetailResponse;
import com.travel.dto.TravelListRequest;
import com.travel.dto.TravelListResponse;
import com.travel.dto.TravelSaveRequest;
import com.travel.entity.Travel;
import com.travel.exception.NotFoundException;
import com.travel.mapper.TravelMapper;
import com.travel.service.TravelService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TravelServiceImpl implements TravelService {

    private final TravelMapper travelMapper;
    private final ObjectMapper objectMapper;

    public TravelServiceImpl(TravelMapper travelMapper, ObjectMapper objectMapper) {
        this.travelMapper = travelMapper;
        this.objectMapper = objectMapper;
    }

    // --- JSON helpers ---

    private List<String> parseJsonArray(String json) {
        if (json == null || json.isBlank()) {
            return Collections.emptyList();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            return Collections.emptyList();
        }
    }

    private String toJsonArray(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(list);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    // --- DTO converters ---

    private TravelListResponse toListResponse(Travel entity) {
        TravelListResponse resp = new TravelListResponse();
        resp.setId(entity.getId());
        resp.setTitle(entity.getTitle());
        resp.setSubtitle(entity.getSubtitle());
        resp.setCoverImage(entity.getCoverImage());
        resp.setExperienceTypes(parseJsonArray(entity.getExperienceTypes()));
        resp.setVisualStyle(entity.getVisualStyle());
        resp.setRarityLevel(entity.getRarityLevel());
        resp.setDestination(entity.getDestination());
        resp.setHighlights(parseJsonArray(entity.getHighlights()));
        resp.setCreatedAt(entity.getCreatedAt());
        return resp;
    }

    private TravelDetailResponse toDetailResponse(Travel entity) {
        TravelDetailResponse resp = new TravelDetailResponse();
        resp.setId(entity.getId());
        resp.setTitle(entity.getTitle());
        resp.setSubtitle(entity.getSubtitle());
        resp.setCoverImage(entity.getCoverImage());
        resp.setDetailImages(parseJsonArray(entity.getDetailImages()));
        resp.setExperienceTypes(parseJsonArray(entity.getExperienceTypes()));
        resp.setVisualStyle(entity.getVisualStyle());
        resp.setRarityLevel(entity.getRarityLevel());
        resp.setDestination(entity.getDestination());
        resp.setContent(entity.getContent());
        resp.setHighlights(parseJsonArray(entity.getHighlights()));
        resp.setCreatedAt(entity.getCreatedAt());
        resp.setUpdatedAt(entity.getUpdatedAt());
        return resp;
    }

    private Travel toEntity(TravelSaveRequest req) {
        Travel entity = new Travel();
        entity.setTitle(req.getTitle());
        entity.setSubtitle(req.getSubtitle());
        entity.setCoverImage(req.getCoverImage());
        entity.setDetailImages(toJsonArray(req.getDetailImages()));
        entity.setExperienceTypes(toJsonArray(req.getExperienceTypes()));
        entity.setVisualStyle(req.getVisualStyle());
        entity.setRarityLevel(req.getRarityLevel());
        entity.setDestination(req.getDestination());
        entity.setContent(req.getContent());
        entity.setHighlights(toJsonArray(req.getHighlights()));
        return entity;
    }

    private void updateEntity(Travel entity, TravelSaveRequest req) {
        entity.setTitle(req.getTitle());
        entity.setSubtitle(req.getSubtitle());
        entity.setCoverImage(req.getCoverImage());
        entity.setDetailImages(toJsonArray(req.getDetailImages()));
        entity.setExperienceTypes(toJsonArray(req.getExperienceTypes()));
        entity.setVisualStyle(req.getVisualStyle());
        entity.setRarityLevel(req.getRarityLevel());
        entity.setDestination(req.getDestination());
        entity.setContent(req.getContent());
        entity.setHighlights(toJsonArray(req.getHighlights()));
    }

    // --- Business methods ---

    @Override
    public PageResult<TravelListResponse> listTravels(TravelListRequest req) {
        Page<Travel> page = new Page<>(req.getPage(), req.getSize());
        IPage<Travel> result = travelMapper.selectPageWithFilter(
                page, req.getExperienceType(), req.getVisualStyle(), req.getRarityLevel());
        PageResult<Travel> raw = PageResult.from(result);
        List<TravelListResponse> converted = result.getRecords().stream()
                .map(this::toListResponse)
                .toList();
        PageResult<TravelListResponse> finalResult = new PageResult<>();
        finalResult.setTotal(raw.getTotal());
        finalResult.setPage(raw.getPage());
        finalResult.setSize(raw.getSize());
        finalResult.setList(converted);
        return finalResult;
    }

    @Override
    public TravelDetailResponse getTravelDetail(Long id) {
        Travel entity = travelMapper.selectById(id);
        if (entity == null) {
            throw new NotFoundException("旅行内容不存在: id=" + id);
        }
        return toDetailResponse(entity);
    }

    @Override
    public TravelDetailResponse createTravel(TravelSaveRequest req) {
        Travel entity = toEntity(req);
        travelMapper.insert(entity);
        Travel created = travelMapper.selectById(entity.getId());
        return toDetailResponse(created);
    }

    @Override
    public TravelDetailResponse updateTravel(Long id, TravelSaveRequest req) {
        Travel entity = travelMapper.selectById(id);
        if (entity == null) {
            throw new NotFoundException("旅行内容不存在: id=" + id);
        }
        updateEntity(entity, req);
        travelMapper.updateById(entity);
        Travel updated = travelMapper.selectById(id);
        return toDetailResponse(updated);
    }

    @Override
    public void deleteTravel(Long id) {
        Travel entity = travelMapper.selectById(id);
        if (entity == null) {
            throw new NotFoundException("旅行内容不存在: id=" + id);
        }
        travelMapper.deleteById(id);
    }
}
