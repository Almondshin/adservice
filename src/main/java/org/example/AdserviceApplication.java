package org.example;

import org.example.adservice.application.service.ad.AdService;
import org.example.adservice.controller.ad.container.AdRequest;
import org.example.adservice.domain.ad.AdRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AdserviceApplication {

    private static Logger LOG = LoggerFactory.getLogger(AdserviceApplication.class);

    public static void main(String[] args) {
        LOG.info("Starting AdserviceApplication");
        SpringApplication.run(AdserviceApplication.class, args);
        LOG.info("Finished AdserviceApplication");
    }

    @Bean
    CommandLineRunner commandLineRunner(AdService adService) {
        return args -> {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime end = LocalDateTime.now().plusDays(20);
            adService.addAd(AdRequest.builder()
                            .adName("광고1")
                            .description("설명1")
                            .rewardAmount(1000)
                            .remainingJoinCount(100)
                            .imageUrl("https://example.com/image1.jpg")
                            .startDate(now)
                            .endDate(end)
                    .build());
            adService.findAds();
        };
    }

}
