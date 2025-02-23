package org.example.adservice.domain.history;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class AdJoinHistoryCompositeId implements Serializable {
    private String userId;
    private String adId;
    private LocalDateTime joinAt;

    public AdJoinHistoryCompositeId(String userId, String adId, LocalDateTime joinAt) {
        this.userId = userId;
        this.adId = adId;
        this.joinAt = joinAt;
    }

    public String getUserId() {
        return userId;
    }

    public String getAdId() {
        return adId;
    }

    public LocalDateTime getJoinAt() {
        return joinAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AdJoinHistoryCompositeId that = (AdJoinHistoryCompositeId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(adId, that.adId) &&
                Objects.equals(joinAt, that.joinAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, adId, joinAt);
    }
}
