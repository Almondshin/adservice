package org.example.adservice.application.service.join;

import org.example.adservice.controller.join.container.AdJoinRequest;
import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.ad.AdRepository;
import org.example.adservice.domain.coreservice.RedisLockPort;
import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.history.AdJoinHistoryRepository;
import org.example.adservice.domain.user.User;
import org.example.adservice.domain.user.UserId;
import org.example.adservice.domain.user.UserRepository;
import org.example.adservice.persistance.redis.config.RedisProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdJoinService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdJoinHistoryRepository adJoinHistoryRepository;
    private final PointAddClient pointAddClient;
    private final RedisLockPort redisLockPort;
    private final RedisProperties redisProperties;

    private static Logger LOG = LoggerFactory.getLogger(AdJoinService.class);
    private static final String AD_JOIN_PREFIX = "ad_join_";

    public AdJoinService(AdRepository adRepository,
            UserRepository userRepository,
            AdJoinHistoryRepository adJoinHistoryRepository,
            PointAddClient pointAddClient,
            RedisLockPort redisLockPort,
            RedisProperties redisProperties) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adJoinHistoryRepository = adJoinHistoryRepository;
        this.pointAddClient = pointAddClient;
        this.redisLockPort = redisLockPort;
        this.redisProperties = redisProperties;
    }

    @Transactional(rollbackFor = Exception.class)
    public void adJoin(AdJoinRequest request) {

        String lockKey = AD_JOIN_PREFIX + request.getAdId();

        String lockToken = redisLockPort.acquireLock(lockKey, redisProperties.getLockExpireTime());
        if (lockToken == null) {
            LOG.warn("Lock 획득 실패 - adId: {}, userId: {}", request.getAdId(), request.getUserId());
            throw new IllegalStateException("다른 요청이 처리 중입니다. 잠시 후 다시 시도해주세요.");
        }
        try {
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
            List<AdJoinHistory> historyList = adJoinHistoryRepository.findByUserId(user.getId());

            if (!ad.canUserJoin(user.getId(), historyList)) {
                throw new IllegalStateException("유저가 해당 광고에 참여할 수 없습니다.");
            }

            if(!user.canJoin()){
                throw new IllegalStateException("유저의 광고 참여 가능 횟수가 부족합니다: " + request.getUserId());
            }

            ad.decreaseJoinCount();
            adRepository.add(ad);

            user.decreaseJoinCount();
            userRepository.add(user);

            AdJoinHistory history = new AdJoinHistory(
                    user.getId(),
                    ad.getId(),
                    LocalDateTime.now(),
                    ad.name(),
                    ad.rewardAmount()
            );
            adJoinHistoryRepository.add(history);

            pointAddClient.addPoint(user.getId(), ad.rewardAmount());

            LOG.info("광고 참여 성공 - adId: {}, userId: {}", request.getAdId(), request.getUserId());
        } catch (Exception e) {
            LOG.error("광고 참여 중 오류 발생 - adId: {}, userId: {}, error: {}", request.getAdId(), request.getUserId(), e.getMessage());
            throw e;
        } finally {
            redisLockPort.releaseLock(lockKey, lockToken);
            LOG.info("Lock 해제 완료 - adId: {}", request.getAdId());
        }
    }
}

