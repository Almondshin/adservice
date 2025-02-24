package org.example.adservice.domain.history;

import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.user.UserId;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
@TypeDefs({
        @TypeDef(name="userId", typeClass=UserId.UserIdJavaType.class),
        @TypeDef(name="adId",   typeClass=AdId.AdIdJavaType.class)
})
public class AdJoinHistoryCompositeId implements Serializable {
    @Type(type = "userId")
    @Column(name = "USER_ID", nullable = false)
    private UserId userId;
    @Type(type = "adId")
    @Column(name = "AD_ID", nullable = false)
    private AdId adId;
    @Column(name = "JOIN_AT", nullable = false)
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
