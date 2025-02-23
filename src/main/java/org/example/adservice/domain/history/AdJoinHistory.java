package org.example.adservice.domain.history;

import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.user.User;
import org.example.common.domain.AggregateRoot;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AD_JOIN_HISTORY")
public class AdJoinHistory extends AggregateRoot<AdJoinHistory, AdJoinHistoryId> {

    @Id
    @Type(type = "adJoinHistoryId")
    @Column(name = "AD_JOIN_HISTORY_ID", nullable = false)
    private AdJoinHistoryId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AD_ID", nullable = false)
    private Ad ad;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "JOIN_AT", nullable = false, updatable = false)
    @OrderBy("joinAt ASC")
    private LocalDateTime joinAt;

    @Column(name = "REWARD_AMOUNT", nullable = false)
    private int rewardAmount;

    @Override
    public AdJoinHistoryId getId() {
        return this.id;
    }

    public AdJoinHistory(AdJoinHistoryId id, Ad ad, User user, LocalDateTime joinAt, int rewardAmount) {
        this.id = id;
        this.ad = ad;
        this.user = user;
        this.joinAt = joinAt;
        this.rewardAmount = rewardAmount;
    }
}
