package org.example.adservice.domain.history;

import org.example.common.domain.StringTypeIdentifier;
import org.example.common.jpa.hibernate.StringTypeIdentifierJavaType;

import java.io.Serializable;


public class AdJoinHistoryId extends StringTypeIdentifier implements Serializable {

    public static AdJoinHistoryId of(String id) {
        return new AdJoinHistoryId(id);
    }

    public AdJoinHistoryId(String adJoinHistoryId) {
        super(adJoinHistoryId);
    }

    public static class AdJoinHistoryJavaType extends StringTypeIdentifierJavaType<AdJoinHistoryId> {
        public AdJoinHistoryJavaType() {
            super(AdJoinHistoryId.class);
        }
    }

}
