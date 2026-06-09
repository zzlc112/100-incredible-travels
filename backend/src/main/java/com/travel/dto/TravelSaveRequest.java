package com.travel.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

import java.util.List;

public class TravelSaveRequest {

    @NotBlank @Size(max = 200)
    private String title;

    @NotBlank @Size(max = 500)
    private String subtitle;

    @NotBlank
    private String coverImage;

    @Size(max = 10)
    private List<String> detailImages;

    @NotEmpty
    private List<String> experienceTypes;

    @NotBlank
    private String visualStyle;

    @NotNull @Min(1) @Max(4)
    private Integer rarityLevel;

    @NotBlank @Size(max = 200)
    private String destination;

    @NotBlank
    private String content;

    @Size(max = 5)
    private List<String> highlights;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
    public String getCoverImage() { return coverImage; }
    public void setCoverImage(String coverImage) { this.coverImage = coverImage; }
    public List<String> getDetailImages() { return detailImages; }
    public void setDetailImages(List<String> detailImages) { this.detailImages = detailImages; }
    public List<String> getExperienceTypes() { return experienceTypes; }
    public void setExperienceTypes(List<String> experienceTypes) { this.experienceTypes = experienceTypes; }
    public String getVisualStyle() { return visualStyle; }
    public void setVisualStyle(String visualStyle) { this.visualStyle = visualStyle; }
    public Integer getRarityLevel() { return rarityLevel; }
    public void setRarityLevel(Integer rarityLevel) { this.rarityLevel = rarityLevel; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public List<String> getHighlights() { return highlights; }
    public void setHighlights(List<String> highlights) { this.highlights = highlights; }
}
