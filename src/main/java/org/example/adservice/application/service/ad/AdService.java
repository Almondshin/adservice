package org.example.adservice.application.service.ad;

import org.example.adservice.controller.ad.container.AdRequest;
import org.example.adservice.controller.ad.container.AdResponse;
import org.example.adservice.controller.exception.exceptions.DuplicateAdNameException;
import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.ad.AdRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdService {
    private static final String AD_ID_PREFIX = "ad-";
    private final AdRepository adRepository;

    public AdService(AdRepository adRepository) {
        this.adRepository = adRepository;
    }

    @Transactional()
    public void addAd(AdRequest request) {

        if (adRepository.existsByName(request.getAdName())){
            throw new DuplicateAdNameException("이미 존재하는 광고명입니다: " + request.getAdName());
        }

        Ad ad = new Ad(
                AdId.of(AD_ID_PREFIX + UUID.randomUUID()),
                request.getAdName(),
                request.getRewardAmount(),
                request.getRemainingJoinCount(),
                request.getStartDate(),
                request.getEndDate(),
                request.getDescription(),
                request.getImageUrl()
        );
        adRepository.add(ad);

    }

    @Transactional(readOnly = true)
    public List<AdResponse> findAds() {
        List<Ad> allAds = adRepository.findAds();
        return Ad.getTopValidAds(allAds).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    private AdResponse toResponse(Ad ad) {
        return AdResponse.builder()
                .id(ad.getId().stringValue())
                .name(ad.name())
                .description(ad.description())
                .rewardAmount(ad.rewardAmount())
                .remainingJoinCount(ad.remainingJoinCount())
                .imageUrl(ad.imageUrl())
                .startDate(ad.startDate())
                .endDate(ad.endDate())
                .build();
    }

}

