package org.example.adservice.domain.ad;


import org.example.common.domain.Repository;

import java.util.List;

public interface AdRepository extends Repository<Ad,AdId> {
    Ad find(AdId id);

    boolean existsByName(String name);

    List<Ad> findAds();
}
