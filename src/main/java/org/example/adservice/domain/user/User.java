package org.example.adservice.domain.user;

import org.example.common.domain.AggregateRoot;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_INFO")
@TypeDef(name = "userId", typeClass = UserId.UserIdJavaType.class)
public class User extends AggregateRoot<User, UserId> {

    @Id
    @Type(type = "userId")
    @Column(name = "USER_ID", nullable = false)
    private UserId id;

    @Column(name = "USER_REMAINING_COUNT", nullable = false)
    private short remainingJoinCount;

    @Override
    public UserId getId() {
        return this.id;
    }

    public User() {}

    public User(UserId id, short remainingJoinCount) {
        this.id = id;
        this.remainingJoinCount = remainingJoinCount;
    }

    public boolean canJoin() {
        return remainingJoinCount > 0;
    }

    public void decreaseJoinCount() {
        if (remainingJoinCount > 0) {
            remainingJoinCount--;
        } else {
            throw new IllegalStateException("참여 가능 횟수가 부족합니다.");
        }
    }


}
