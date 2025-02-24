package org.example.adservice;

import org.example.adservice.application.service.join.AdJoinService;
import org.example.adservice.application.service.join.PointAddClient;
import org.example.adservice.controller.join.container.AdJoinRequest;
import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.ad.AdRepository;
import org.example.adservice.domain.history.AdJoinHistoryRepository;
import org.example.adservice.domain.user.User;
import org.example.adservice.domain.user.UserId;
import org.example.adservice.domain.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdJoinServiceTest {

    @Mock
    private AdRepository adRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdJoinHistoryRepository adJoinHistoryRepository;

    @Mock
    private PointAddClient pointAddClient;

    @InjectMocks
    private AdJoinService adJoinService;

    private Ad testAd;
    private User testUser;

    @BeforeEach
    void setUp() {
        testAd = new Ad(
                AdId.of("ad1"),
                "테스트 광고",
                100,
                5,
                LocalDateTime.now().minusDays(1),
                LocalDateTime.now().plusDays(5),
                "테스트 설명",
                "https://test.com/ad.jpg"
        );

        testUser = new User(UserId.of("user1"), (short) 5);
    }

    @Test
    void 광고_참여_성공() {
        AdJoinRequest request = new AdJoinRequest("ad1", "user1");

        when(adRepository.find(AdId.of("ad1"))).thenReturn(testAd);
        when(userRepository.find(UserId.of("user1"))).thenReturn(testUser);

        adJoinService.adJoin(request);

        verify(adRepository).add(testAd);
        verify(adJoinHistoryRepository).add(any());
        verify(pointAddClient).addPoint(testUser.getId(), testAd.rewardAmount());
    }

    @Test
    void 광고_없음_예외발생() {
        AdJoinRequest request = new AdJoinRequest("ad999", "user1");

        when(adRepository.find(AdId.of("ad999"))).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> adJoinService.adJoin(request));
        assertEquals("미등록 광고입니다: ad999", exception.getMessage());
    }

    @Test
    void 광고_참여_횟수_부족_예외발생() {
        testAd = new Ad(AdId.of("ad1"), "테스트 광고", 100, 0, testAd.startDate(), testAd.endDate(), testAd.description(), testAd.imageUrl());
        AdJoinRequest request = new AdJoinRequest("ad1", "user1");

        when(adRepository.find(AdId.of("ad1"))).thenReturn(testAd);

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> adJoinService.adJoin(request));
        assertEquals("광고 참여 가능 횟수가 0회라 참여 불가합니다: ad1", exception.getMessage());
    }

    @Test
    void 유저_없음_예외발생() {
        AdJoinRequest request = new AdJoinRequest("ad1", "user999");

        when(adRepository.find(AdId.of("ad1"))).thenReturn(testAd);
        when(userRepository.find(UserId.of("user999"))).thenReturn(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> adJoinService.adJoin(request));
        assertEquals("존재하지 않는 유저입니다: user999", exception.getMessage());
    }
}