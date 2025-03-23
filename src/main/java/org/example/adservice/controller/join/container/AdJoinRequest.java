package org.example.adservice.controller.join.container;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class AdJoinRequest {

    @NotBlank(message = "광고 ID는 필수 입력값입니다.")
    private String adId;
    @NotNull(message = "유저 ID는 필수 입력값입니다.")
    private String userId;

    @Builder
    public AdJoinRequest(String adId, String userId) {
        this.adId = adId;
        this.userId = userId;
    }
}
