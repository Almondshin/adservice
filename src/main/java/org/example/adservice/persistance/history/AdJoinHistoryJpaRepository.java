package org.example.adservice.persistance.history;

import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.history.AdJoinHistoryCompositeId;
import org.example.adservice.domain.user.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

interface AdJoinHistoryJpaRepository extends JpaRepository<AdJoinHistory, AdJoinHistoryCompositeId> {
    Page<AdJoinHistory> findByIdUserIdAndIdJoinAtBetween(UserId userId, LocalDateTime start, LocalDateTime end, Pageable pageable);
}
