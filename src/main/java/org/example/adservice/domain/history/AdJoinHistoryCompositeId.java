package org.example.adservice.domain.history;

import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.user.UserId;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class AdJoinHistoryCompositeId implements Serializable {
    private UserId userId;
    private AdId adId;
    private LocalDateTime joinAt;

    public AdJoinHistoryCompositeId(UserId userId, AdId adId, LocalDateTime joinAt) {
        this.userId = userId;
        this.adId = adId;
        this.joinAt = joinAt;
    }

    public AdJoinHistoryCompositeId() {

    }

    public UserId getUserId() {
        return userId;
    }

    public AdId getAdId() {
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
