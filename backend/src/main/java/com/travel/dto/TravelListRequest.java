package com.travel.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public class TravelListRequest {

    private String experienceType;

    private String visualStyle;

    @Min(1) @Max(4)
    private Integer rarityLevel;

    @Min(1)
    private Integer page = 1;

    @Min(1) @Max(100)
    private Integer size = 20;

    public String getExperienceType() { return experienceType; }
    public void setExperienceType(String experienceType) { this.experienceType = experienceType; }
    public String getVisualStyle() { return visualStyle; }
    public void setVisualStyle(String visualStyle) { this.visualStyle = visualStyle; }
    public Integer getRarityLevel() { return rarityLevel; }
    public void setRarityLevel(Integer rarityLevel) { this.rarityLevel = rarityLevel; }
    public Integer getPage() { return page; }
    public void setPage(Integer page) { this.page = page; }
    public Integer getSize() { return size; }
    public void setSize(Integer size) { this.size = size; }
}
