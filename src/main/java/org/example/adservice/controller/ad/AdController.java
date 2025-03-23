package org.example.adservice.controller.ad;

import org.example.adservice.application.service.ad.AdService;
import org.example.adservice.controller.ad.container.AdRequest;
import org.example.adservice.controller.ad.container.AdResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ads")
public class AdController {
    private final AdService adService;

    public AdController(AdService adService) {
        this.adService = adService;
    }

    @PostMapping
    public ResponseEntity<Void> createAd(@Valid @RequestBody AdRequest request) {
        adService.addAd(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AdResponse>> getAds() {
        return ResponseEntity.ok().body(adService.findAds());
    }

}
