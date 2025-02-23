package org.example.adservice.domain.history;

import org.example.adservice.domain.user.UserId;
import org.example.common.domain.Repository;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.time.LocalDateTime;

public interface AdJoinHistoryRepository extends Repository<AdJoinHistory, AdJoinHistoryId> {
    AdJoinHistory find(AdJoinHistoryId id);

    Page<AdJoinHistory> findByUserIdAndJoinAt(
            UserId userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable
    );
}
