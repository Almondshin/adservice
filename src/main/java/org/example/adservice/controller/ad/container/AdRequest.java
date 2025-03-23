package org.example.adservice.controller.ad.container;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class AdRequest {

    @NotBlank(message = "광고명은 필수 입력값입니다.")
    private String adName;

    @NotBlank(message = "광고 설명은 필수 입력값입니다.")
    private String description;

    @NotNull(message = "광고 등록시 적립 액수는 필수 입력값입니다.")
    @Min(value = 1, message = "적립 액수는 1 이상이어야 합니다.")
    private int rewardAmount;

    @NotNull(message = "광고 참여 가능 횟수는 필수 입력값입니다.")
    @Min(value = 1, message = "참여 가능 횟수는 최소 1 이상이어야 합니다.")
    private int remainingJoinCount;

    @NotBlank(message = "광고 이미지 URL은 필수 입력값입니다.")
    private String imageUrl;

    @NotNull(message = "광고 시작일은 필수 입력값입니다.")
    private LocalDateTime startDate;

    @NotNull(message = "광고 종료일은 필수 입력값입니다.")
    private LocalDateTime endDate;


    @Builder
    public AdRequest(String adName, String description, int rewardAmount, int remainingJoinCount, String imageUrl, LocalDateTime startDate, LocalDateTime endDate) {
        this.adName = adName;
        this.description = description;
        this.rewardAmount = rewardAmount;
        this.remainingJoinCount = remainingJoinCount;
        this.imageUrl = imageUrl;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}

