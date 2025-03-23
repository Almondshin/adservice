package org.example.adservice.domain.user;

import org.example.common.domain.Repository;

public interface UserRepository extends Repository<User, UserId> {
    User find(UserId id);
}
