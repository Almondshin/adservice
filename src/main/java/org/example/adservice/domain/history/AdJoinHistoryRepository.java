package org.example.adservice.domain.history;

import org.example.adservice.domain.user.UserId;
import org.example.common.domain.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface AdJoinHistoryRepository extends Repository<AdJoinHistory, AdJoinHistoryCompositeId> {
    Page<AdJoinHistory> findByUserIdAndJoinAt(
            UserId userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable
    );
}
