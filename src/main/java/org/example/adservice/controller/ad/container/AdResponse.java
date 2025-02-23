package org.example.adservice.controller.ad.container;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdResponse {
    private String id;
    private String name;
    private String description;
    private Integer rewardAmount;
    private Integer remainingJoinCount;
    private String imageUrl;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public AdResponse(String id, String name, String description, Integer rewardAmount, Integer remainingJoinCount, String imageUrl, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rewardAmount = rewardAmount;
        this.remainingJoinCount = remainingJoinCount;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
