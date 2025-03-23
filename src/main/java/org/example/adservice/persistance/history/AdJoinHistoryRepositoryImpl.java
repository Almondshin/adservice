package org.example.adservice.persistance.history;

import org.example.adservice.domain.history.AdJoinHistory;
import org.example.adservice.domain.history.AdJoinHistoryCompositeId;
import org.example.adservice.domain.history.AdJoinHistoryRepository;
import org.example.adservice.domain.user.UserId;
import org.example.common.jpa.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class AdJoinHistoryRepositoryImpl extends BaseRepository<AdJoinHistory, AdJoinHistoryCompositeId, AdJoinHistoryJpaRepository> implements AdJoinHistoryRepository {
    public AdJoinHistoryRepositoryImpl(AdJoinHistoryJpaRepository repository) {
        super(repository);
    }

    @Override
    public Page<AdJoinHistory> findByUserIdAndJoinAt(UserId userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return repository.findByIdUserIdAndIdJoinAtBetween(userId, startDate, endDate, pageable);
    }

    @Override
    public List<AdJoinHistory> findByUserId(UserId userId) {
        return repository.findByIdUserId(userId);
    }


}
