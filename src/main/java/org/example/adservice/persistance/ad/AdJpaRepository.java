package org.example.adservice.persistance.ad;

import org.example.adservice.domain.ad.Ad;
import org.example.adservice.domain.ad.AdId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface AdJpaRepository extends JpaRepository<Ad, AdId> {

    List<Ad> findAll();

    boolean existsByName(String name);
}
