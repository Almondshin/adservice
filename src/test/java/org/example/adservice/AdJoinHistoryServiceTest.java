package org.example.adservice;

import org.example.adservice.application.service.history.AdJoinHistoryService;
import org.example.adservice.controller.history.container.AdJoinHistoryRequest;
import org.example.adservice.controller.history.container.AdJoinHistoryResponse;
import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.history.AdJoinHistoryCompositeId;
import org.example.adservice.domain.history.AdJoinHistoryRepository;
import org.example.adservice.domain.user.User;
import org.example.adservice.domain.user.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdJoinHistoryServiceTest {

    @Mock
    private AdJoinHistoryRepository adJoinHistoryRepository;

    @InjectMocks
    private AdJoinHistoryService adJoinHistoryService;

    private AdJoinHistory history1;
    private AdJoinHistory history2;
    private User testUser;
    private Ad testAd;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();

        // 실제 User 및 Ad 객체 생성 (Mock 데이터)
        testUser = new User(UserId.of("user1"), (short) 5);
        testAd = new Ad(AdId.of("ad1"), "테스트 광고", 200, 10, now.minusDays(10), now.plusDays(10), "테스트 설명", "https://test.com/ad.jpg");

        history1 = new AdJoinHistory(
                testUser.getId(),
                testAd.getId(),
                now.minusDays(3),
                testAd.name(),
                100
        );

        history2 = new AdJoinHistory(
                testUser.getId(),
                testAd.getId(),
                now.minusDays(1),
                testAd.name(),
                200
        );
    }

    @Test
    void 광고_참여_이력_조회_성공() {
        AdJoinHistoryRequest request = new AdJoinHistoryRequest("user1", LocalDateTime.now().minusDays(7), LocalDateTime.now(), 0);

        when(adJoinHistoryRepository.findByUserIdAndJoinAt(any(), any(), any(), any()))
                .thenReturn(new PageImpl<>(List.of(history1, history2), PageRequest.of(0, 50), 2));

        Page<AdJoinHistoryResponse> result = adJoinHistoryService.findJoinHistoryByUserId(request);

        assertEquals(2, result.getTotalElements());
        assertEquals(100, result.getContent().get(0).getRewardAmount());
        assertEquals(200, result.getContent().get(1).getRewardAmount());
    }


    @Test
    void 광고_참여_이력_조회_실패_유효하지_않은_유저ID() {
        AdJoinHistoryRequest request = new AdJoinHistoryRequest("user1234", LocalDateTime.now().minusDays(7), LocalDateTime.now(), 0);

        assertThrows(NullPointerException.class, () -> adJoinHistoryService.findJoinHistoryByUserId(request));
    }


    @Test
    void 광고_참여_이력_조회_실패_조회기간_잘못됨() {
        AdJoinHistoryRequest request = new AdJoinHistoryRequest("user1", LocalDateTime.now(), LocalDateTime.now().minusDays(7), 0);

        assertThrows(IllegalArgumentException.class, () -> adJoinHistoryService.findJoinHistoryByUserId(request));
    }

}
