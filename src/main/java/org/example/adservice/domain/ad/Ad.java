package org.example.adservice.domain.ad;

import jakarta.persistence.Column;
import org.example.common.domain.AggregateRoot;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "AD_INFO")
public class Ad extends AggregateRoot<Ad, AdId> {

    @Id
    @Type(type = "adId")
    @Column(name = "AD_ID", nullable = false)
    private AdId id;

    @Column(name = "AD_NAME", nullable = false, unique = true)
    private String name;

    @Column(name = "AD_DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "AD_REWARD_AMOUNT", nullable = false)
    private int rewardAmount;

    @Column(name = "AD_REMAINING_COUNT", nullable = false)
    private short remainingJoinCount;

    @Column(name = "AD_IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @Embedded
    private AdJoinPolicy adJoinPolicy;

    protected Ad() {}

    public Ad(AdId id, String name, int rewardAmount, short remainingJoinCount,
            LocalDateTime startDate, LocalDateTime endDate, String description,
            String imageUrl, AdJoinPolicy adJoinPolicy) {
        this.id = id;
        this.name = name;
        this.rewardAmount = rewardAmount;
        this.remainingJoinCount = remainingJoinCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.imageUrl = imageUrl;
        this.adJoinPolicy = adJoinPolicy;
    }

    @Override
    public AdId getId() {
        return this.id;
    }

    public boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return remainingJoinCount > 0 && now.isAfter(startDate) && now.isBefore(endDate);
    }

    public void decreaseJoinCount() {
        if (remainingJoinCount > 0) {
            remainingJoinCount--;
        } else {
            throw new IllegalStateException("광고 참여 가능 횟수가 초과되었습니다.");
        }
    }



}

