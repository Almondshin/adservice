package org.example.adservice.application.service.join;

import org.example.adservice.domain.user.UserId;

public interface PointAddClient {
    void addPoint(UserId userId, int rewardAmount);
}
