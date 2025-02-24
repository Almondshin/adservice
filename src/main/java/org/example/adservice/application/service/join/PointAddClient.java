package org.example.adservice.application.service.join;

import org.example.adservice.domain.user.UserId;
import org.springframework.stereotype.Component;

@Component
public interface PointAddClient {
    void addPoint(UserId userId, int rewardAmount);
}
