package org.example.adservice;

import org.example.adservice.application.service.ad.AdService;
import org.example.adservice.controller.ad.container.AdResponse;
import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.ad.AdRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdServiceTest {

    @Mock
    private AdRepository adRepository;

    @InjectMocks
    private AdService adService;

    private Ad validAd;
    private Ad expiredAd;
    private Ad exhaustedAd;

    @BeforeEach
    void setUp() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime validStart = now.minusDays(1);
        LocalDateTime validEnd = now.plusDays(5);
        LocalDateTime expiredEnd = now.minusDays(1);

        validAd = new Ad(
                AdId.of("valid-ad"),
                "유효한 광고",
                200,
                5,
                validStart,
                validEnd,
                "유효한 광고 설명",
                "https://example.com/image_valid.jpg"
        );

        expiredAd = new Ad(
                AdId.of("expired-ad"),
                "만료된 광고",
                150,
                5,
                validStart,
                expiredEnd,
                "만료된 광고 설명",
                "https://example.com/image_expired.jpg"
        );

        exhaustedAd = new Ad(
                AdId.of("exhausted-ad"),
                "참여 가능 횟수 0인 광고",
                300,
                0,  // 참여 가능 횟수 0
                validStart,
                validEnd,
                "참여 제한 광고 설명",
                "https://example.com/image_exhausted.jpg"
        );
    }

    @Test
    void 광고_생성_실패_참여_가능_횟수가_음수이면_예외발생() {
        assertThrows(IllegalArgumentException.class, () -> new Ad(
                AdId.of("ad-1"),
                "테스트 광고",
                100,
                -1,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "설명",
                "https://example.com/image.jpg"
        ));
    }

    @Test
    void 광고_생성_실패_시작일이_종료일보다_뒤면_예외발생() {
        assertThrows(IllegalArgumentException.class, () -> new Ad(
                AdId.of("ad-1"),
                "테스트 광고",
                100,
                5,
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(5),
                "설명",
                "https://example.com/image.jpg"
        ));
    }

    @Test
    void 광고_참여_가능_횟수가_0이면_예외발생() {
        Ad ad = new Ad(
                AdId.of("ad-1"),
                "테스트 광고",
                100,
                0,
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(7),
                "설명",
                "https://example.com/image.jpg"
        );

        // 0에서 감소하면 예외 발생해야 함
        assertThrows(IllegalStateException.class, ad::decreaseJoinCount);
    }


    @Test
    void 광고_조회_성공_유효한_광고만_포함() {
        when(adRepository.findAds()).thenReturn(List.of(validAd, expiredAd, exhaustedAd));

        List<AdResponse> result = adService.findAds();

        //유효한 광고만 필터링 되었는지 확인
        assertEquals(1, result.size());
        assertEquals("유효한 광고", result.get(0).getName());
    }

    @Test
    void 광고_조회_성공_유효한_광고가_10개_미만이면_있는_개수만_반환() {
        List<Ad> fewAds = List.of(validAd, validAd, validAd); // 3개만 존재

        when(adRepository.findAds()).thenReturn(fewAds);

        List<AdResponse> result = adService.findAds();

        // 3개만 반환되는지 검증
        assertEquals(3, result.size());
    }


    @Test
    void 광고_조회_성공_최대_10개만_조회() {
        // 11개 (최대 10개만 반환돼야 함)
        List<Ad> manyAds = List.of(
                validAd, validAd, validAd, validAd, validAd,
                validAd, validAd, validAd, validAd, validAd,
                validAd
        );

        when(adRepository.findAds()).thenReturn(manyAds);

        List<AdResponse> result = adService.findAds();

        //  최대 10개까지 조회되는지 검증
        assertEquals(10, result.size());
    }

    @Test
    void 광고_조회_성공_보상금_높은_순서로_정렬됨() {
        Ad highRewardAd = new Ad(
                AdId.of("high-reward-ad"),
                "높은 보상 광고",
                1000,
                5,
                validAd.startDate(),
                validAd.endDate(),
                "높은 보상 광고 설명",
                "https://example.com/image_high.jpg"
        );

        when(adRepository.findAds()).thenReturn(List.of(validAd, highRewardAd));

        List<AdResponse> result = adService.findAds();

        // 보상금 높은 광고가 첫 번째에 위치해야 함
        assertEquals("높은 보상 광고", result.get(0).getName());
        assertEquals("유효한 광고", result.get(1).getName());
    }
}
