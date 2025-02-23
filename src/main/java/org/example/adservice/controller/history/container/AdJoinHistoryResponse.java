package org.example.adservice.controller.history.container;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class AdJoinHistoryResponse {
    private LocalDateTime joinAt;
    private String userId;
    private String adId;
    private String adName;
    private int rewardAmount;

    @Builder
    public AdJoinHistoryResponse(LocalDateTime joinAt, String userId, String adId, String adName, int rewardAmount) {
        this.joinAt = joinAt;
        this.userId = userId;
        this.adId = adId;
        this.adName = adName;
        this.rewardAmount = rewardAmount;
    }
}
