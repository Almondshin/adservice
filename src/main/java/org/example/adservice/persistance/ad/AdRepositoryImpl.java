package org.example.adservice.persistance.ad;

import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.example.adservice.domain.ad.AdRepository;
import org.example.common.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdRepositoryImpl extends BaseRepository<Ad, AdId, AdJpaRepository> implements AdRepository {
    public AdRepositoryImpl(AdJpaRepository repository) {
        super(repository);
    }

    @Override
    public boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public List<Ad> findAds() {
        return repository.findAll();
    }

}
