package com.travel.dto;

import java.time.LocalDateTime;
import java.util.List;

public class TravelListResponse {

    private Long id;
    private String title;
    private String subtitle;
    private String coverImage;
    private List<String> experienceTypes;
    private String visualStyle;
    private Integer rarityLevel;
    private String destination;
    private List<String> highlights;
    private LocalDateTime createdAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public List<String> getExperienceTypes() { return experienceTypes; }
    public void setExperienceTypes(List<String> experienceTypes) { this.experienceTypes = experienceTypes; }
    public String getVisualStyle() { return visualStyle; }
    public void setVisualStyle(String visualStyle) { this.visualStyle = visualStyle; }
    public Integer getRarityLevel() { return rarityLevel; }
    public void setRarityLevel(Integer rarityLevel) { this.rarityLevel = rarityLevel; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
