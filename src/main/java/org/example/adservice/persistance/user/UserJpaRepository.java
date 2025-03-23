package org.example.adservice.persistance.user;

import org.example.adservice.domain.user.User;
import org.example.adservice.domain.user.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

interface UserJpaRepository extends JpaRepository<User, UserId> {
}
