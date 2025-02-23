package org.example.adservice.application.service.history;


import org.example.adservice.controller.history.container.AdJoinHistoryRequest;
import org.example.adservice.controller.history.container.AdJoinHistoryResponse;
import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.history.AdJoinHistoryRepository;
import org.example.adservice.domain.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdJoinHistoryService {
    private static final Integer PAGE_SIZE = 50;

    private final AdJoinHistoryRepository adJoinHistoryRepository;

    public AdJoinHistoryService(AdJoinHistoryRepository repository) {
        this.adJoinHistoryRepository = repository;
    }

    @Transactional(readOnly = true)
    public Page<AdJoinHistoryResponse> findJoinHistoryByUserId(AdJoinHistoryRequest request) {
        if (request.getStartDate().isAfter(request.getEndDate())) {
            throw new IllegalArgumentException("조회 시작일(startDate)은 종료일(endDate)보다 이전이어야 합니다.");
        }
        Pageable pageable = PageRequest.of(request.getPage(), PAGE_SIZE);
        return adJoinHistoryRepository.findByUserIdAndJoinAt(
                UserId.of(request.getUserId()), request.getStartDate(), request.getEndDate(), pageable
        ).map(this::toResponse);
    }

    private AdJoinHistoryResponse toResponse(AdJoinHistory history) {
        return AdJoinHistoryResponse.builder()
                .userId(history.getUserId().stringValue())
                .adId(history.getAdId().stringValue())
                .joinAt(history.getJoinAt())
                .adName(history.getAdName())
                .rewardAmount(history.getRewardAmount())
                .build();
    }


}
