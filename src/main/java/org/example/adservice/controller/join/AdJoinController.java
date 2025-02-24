package org.example.adservice.controller.join;

import org.example.adservice.application.service.join.AdJoinService;
import org.example.adservice.controller.join.container.AdJoinRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/ads/join")
public class AdJoinController {

    private final AdJoinService adJoinService;

    public AdJoinController(AdJoinService adJoinService) {
        this.adJoinService = adJoinService;
    }

    @PostMapping
    public ResponseEntity<Void> joinAd(@Valid @RequestBody AdJoinRequest request) {
        adJoinService.adJoin(request);
        return ResponseEntity.ok().build();
    }
}
