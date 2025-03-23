package org.example.adservice.domain.ad;

public enum PolicyType {
    ALREADY_JOINED_AD,    // 특정 광고 참여 이력 필요
    MINIMUM_JOIN, // 최소 참여 횟수 필요
    FIRST_TIME_ONLY     // 처음 참여하는 유저만 가능
}
