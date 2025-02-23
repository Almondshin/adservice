package org.example.adservice.domain.user;

import org.example.common.domain.StringTypeIdentifier;
import org.example.common.jpa.hibernate.StringTypeIdentifierJavaType;
import org.hibernate.annotations.TypeDef;

@TypeDef(name = "userId", typeClass = UserId.UserIdJavaType.class)
public class UserId extends StringTypeIdentifier {

    public static UserId of(String id) {
        return new UserId(id);
    }

    public UserId(String id) {
        super(id);
    }

    public static class UserIdJavaType extends StringTypeIdentifierJavaType<UserId> {
        public UserIdJavaType() {
            super(UserId.class);
        }
    }

}
