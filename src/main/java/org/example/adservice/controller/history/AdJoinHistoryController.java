package org.example.adservice.controller.history;

import lombok.RequiredArgsConstructor;
import org.example.adservice.application.service.history.AdJoinHistoryService;
import org.example.adservice.controller.history.container.AdJoinHistoryRequest;
import org.example.adservice.controller.history.container.AdJoinHistoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ads/history")
@RequiredArgsConstructor
public class AdJoinHistoryController {

    private final AdJoinHistoryService adJoinHistoryService;

    @PostMapping
    public ResponseEntity<PageResponse> getJoinHistory(@Valid @RequestBody AdJoinHistoryRequest request) {
        Page<AdJoinHistoryResponse> historyPage = adJoinHistoryService.findJoinHistoryByUserId(request);
        System.out.println(historyPage);
        return ResponseEntity.ok(new PageResponse(historyPage));
    }


    class PageResponse {
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
        private List<AdJoinHistoryResponse> content;

        public PageResponse(Page<AdJoinHistoryResponse> pageData) {
            this.page = pageData.getNumber();
            this.size = pageData.getSize();
            this.totalElements = pageData.getTotalElements();
            this.totalPages = pageData.getTotalPages();
            this.content = pageData.getContent();
        }

        public int getPage() {
            return page;
        }

        public int getSize() {
            return size;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public List<AdJoinHistoryResponse> getContent() {
            return content;
        }
    }
}
