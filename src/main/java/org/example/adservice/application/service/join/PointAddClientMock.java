package org.example.adservice.application.service.join;

import org.example.adservice.domain.user.UserId;
import org.springframework.stereotype.Component;

@Component
public class PointAddClientMock implements PointAddClient {

    @Override
    public void addPoint(UserId userId, int rewardAmount) {
        System.out.println("PointAddClientMock.addPoint() 호출 userId: "
                + userId.stringValue() + ", rewardAmount: " + rewardAmount);
    }
}
