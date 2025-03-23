package org.example.adservice.persistance.user;

import org.example.adservice.domain.user.User;
import org.example.adservice.domain.user.UserId;
import org.example.adservice.domain.user.UserRepository;
import org.example.common.jpa.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl extends BaseRepository<User, UserId, UserJpaRepository> implements UserRepository {
    public UserRepositoryImpl(UserJpaRepository repository) {
        super(repository);
    }
}
