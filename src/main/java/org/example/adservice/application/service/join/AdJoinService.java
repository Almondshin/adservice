package org.example.adservice.application.service.join;

import org.example.adservice.controller.join.container.AdJoinRequest;
import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.ad.AdRepository;
import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.history.AdJoinHistoryRepository;
import org.example.adservice.domain.user.User;
import org.example.adservice.domain.user.UserId;
import org.example.adservice.domain.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AdJoinService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdJoinHistoryRepository adJoinHistoryRepository;
    private final PointAddClient pointAddClient;

    public AdJoinService(AdRepository adRepository,
            UserRepository userRepository,
            AdJoinHistoryRepository adJoinHistoryRepository,
            PointAddClient pointAddClient) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adJoinHistoryRepository = adJoinHistoryRepository;
        this.pointAddClient = pointAddClient;
    }

    @Transactional
    public void adJoin(AdJoinRequest request) {
        Ad ad = adRepository.find(AdId.of(request.getAdId()));

        if (ad == null) {
            throw new IllegalArgumentException("미등록 광고입니다: " + request.getAdId());
        }

        if (ad.remainingJoinCount() <= 0) {
            throw new IllegalStateException("광고 참여 가능 횟수가 0회라 참여 불가합니다: " + request.getAdId());
        }

        User user = userRepository.find(UserId.of(request.getUserId()));
        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 유저입니다: " + request.getUserId());
        }

        ad.decreaseJoinCount();
        adRepository.add(ad);

        AdJoinHistory history = new AdJoinHistory(
                user.getId(),
                ad.getId(),
                LocalDateTime.now(),
                ad.name(),
                ad.rewardAmount()
        );
        adJoinHistoryRepository.add(history);

        pointAddClient.addPoint(user.getId(), ad.rewardAmount());
    }
}

