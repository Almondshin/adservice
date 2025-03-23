package org.example.adservice.controller.history.container;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
public class AdJoinHistoryRequest {

    @NotNull(message = "유저 ID는 필수 입력값입니다.")
    private String userId;

    @NotNull(message = "조회 시작일은 필수 입력값입니다.")
    private LocalDateTime startDate;

    @NotNull(message = "조회 종료일은 필수 입력값입니다.")
    private LocalDateTime endDate;

    private Integer page;

    @Builder
    public AdJoinHistoryRequest(String userId, LocalDateTime startDate, LocalDateTime endDate, Integer page) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.page = (page != null) ? page : 0;
    }
}
