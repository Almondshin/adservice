package org.example.adservice.domain.history;

import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.user.UserId;
import org.example.common.domain.AggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AD_JOIN_HISTORY")
@IdClass(AdJoinHistoryCompositeId.class)
public class AdJoinHistory extends AggregateRoot<AdJoinHistory, AdJoinHistoryCompositeId> {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private UserId userId;

    @Id
    @Column(name = "AD_ID", nullable = false)
    private AdId adId;

    @Id
    @Column(name = "JOIN_AT", nullable = false)
    private LocalDateTime joinAt;

    @Column(name = "AD_NAME", nullable = false)
    private String adName;

    @Column(name = "REWARD_AMOUNT", nullable = false)
    private int rewardAmount;

    @Transient
    private AdJoinHistoryCompositeId adJoinHistoryCompositeId;

    @Override
    public AdJoinHistoryCompositeId getId() {
        return new AdJoinHistoryCompositeId(userId.stringValue(), adId.stringValue(), joinAt);
    }

    public UserId getUserId() {
        return userId;
    }

    public AdId getAdId() {
        return adId;
    }

    public String getAdName() {
        return adName;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public LocalDateTime getJoinAt() {
        return joinAt;
    }

    public AdJoinHistory() {
    }

    public AdJoinHistory(UserId userId, AdId adId, LocalDateTime joinAt, String adName, int rewardAmount) {
        this.userId = userId;
        this.adId = adId;
        this.joinAt = joinAt;
        this.adName = adName;
        this.rewardAmount = rewardAmount;
    }
}


