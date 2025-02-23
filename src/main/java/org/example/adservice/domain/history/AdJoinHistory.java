package org.example.adservice.domain.history;

import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.user.UserId;
import org.example.common.domain.AggregateRoot;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AD_JOIN_HISTORY")
public class AdJoinHistory extends AggregateRoot<AdJoinHistory, AdJoinHistoryCompositeId> {

    @EmbeddedId
    private AdJoinHistoryCompositeId id;

    @Column(name = "AD_NAME", nullable = false)
    private String adName;

    @Column(name = "REWARD_AMOUNT", nullable = false)
    private int rewardAmount;

    @Override
    public AdJoinHistoryCompositeId getId() {
        return new AdJoinHistoryCompositeId(id.getUserId(), id.getAdId(), id.getJoinAt());
    }


    public String getAdName() {
        return adName;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }

    public AdJoinHistory() {
    }

    public AdJoinHistory(UserId userId, AdId adId, LocalDateTime joinAt, String adName, int rewardAmount) {
        this.id = new AdJoinHistoryCompositeId(userId, adId, joinAt);
        this.adName = adName;
        this.rewardAmount = rewardAmount;
    }
}


