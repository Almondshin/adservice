package org.example.adservice.domain.ad;

import lombok.ToString;
import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.user.UserId;
import org.example.common.domain.AggregateRoot;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "AD_INFO")
@TypeDef(name = "adId", typeClass = AdId.AdIdJavaType.class)
@ToString
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
    private int remainingJoinCount;

    @Column(name = "AD_IMAGE_URL", nullable = false)
    private String imageUrl;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "AD_ID")
    private List<Policy> policies = new ArrayList<>();

    public boolean canUserJoin(UserId userId, List<AdJoinHistory> historyList) {
        return policies.stream()
                .allMatch(policy -> policy.isSatisfiedBy(userId, historyList));
    }


    @Override
    public AdId getId() {
        return this.id;
    }

    public String name() { return this.name; }
    public String description() { return this.description; }
    public int rewardAmount() { return this.rewardAmount; }
    public int remainingJoinCount() { return this.remainingJoinCount; }
    public String imageUrl() { return this.imageUrl; }
    public LocalDateTime startDate() { return this.startDate; }
    public LocalDateTime endDate() { return this.endDate; }

    public Ad() {}

    public Ad(AdId id, String name, int rewardAmount, int remainingJoinCount,
            LocalDateTime startDate, LocalDateTime endDate, String description,
            String imageUrl) {

        if (remainingJoinCount < 0) {
            throw new IllegalArgumentException("참여 가능 횟수는 0 이상이어야 합니다.");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("광고 시작일은 종료일보다 이전이어야 합니다.");
        }

        this.id = id;
        this.name = name;
        this.rewardAmount = rewardAmount;
        this.remainingJoinCount = remainingJoinCount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.imageUrl = imageUrl;
    }


    private boolean isActive() {
        LocalDateTime now = LocalDateTime.now();
        return remainingJoinCount > 0 && now.isAfter(startDate) && now.isBefore(endDate);
    }

    public void decreaseJoinCount() {
        if (remainingJoinCount <= 0) {
            throw new IllegalStateException("광고 참여 가능 횟수가 초과되었습니다.");
        }
        remainingJoinCount--;
    }

    private static final int MAX_ADS = 10;

    public static List<Ad> getTopValidAds(List<Ad> ads) {
        return ads.stream()
                .filter(Ad::isActive)
                .sorted(Comparator.comparingInt(Ad::rewardAmount).reversed())
                .limit(MAX_ADS)
                .collect(Collectors.toList());
    }

}

