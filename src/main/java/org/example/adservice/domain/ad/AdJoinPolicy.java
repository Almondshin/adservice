package org.example.adservice.domain.ad;

import lombok.Getter;
import org.example.adservice.domain.user.User;
import org.example.common.domain.ValueObject;

import javax.persistence.Embeddable;

@Getter
@Embeddable
public class AdJoinPolicy extends ValueObject<AdJoinPolicy> {

    private final boolean firstTime;
    private final int maxJoinCount;
    private final AdId alreadyJoined;

    protected AdJoinPolicy() {
        this.firstTime = false;
        this.maxJoinCount = -1;
        this.alreadyJoined = null;
    }

    public AdJoinPolicy(boolean firstTime, int maxJoinCount, AdId alreadyJoined) {
        this.firstTime = firstTime;
        this.maxJoinCount = maxJoinCount;
        this.alreadyJoined = alreadyJoined;
    }

    public boolean isFirstTimeOnly() {
        return firstTime;
    }

    public boolean hasJoinCountLimit() {
        return maxJoinCount > 0;
    }

    public boolean requiresSpecificAd() {
        return alreadyJoined != null;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[]{firstTime, maxJoinCount, alreadyJoined};
    }
}

