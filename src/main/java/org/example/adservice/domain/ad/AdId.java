package org.example.adservice.domain.ad;

import org.example.common.domain.StringTypeIdentifier;
import org.example.common.jpa.hibernate.StringTypeIdentifierJavaType;
import org.hibernate.annotations.TypeDef;

@TypeDef(name = "adId", typeClass = AdId.AdIdJavaType.class)
public class AdId extends StringTypeIdentifier {

    public static AdId of(String id) {
        return new AdId(id);
    }

    public AdId(String adId) {
        super(adId);
    }

    public static class AdIdJavaType extends StringTypeIdentifierJavaType<AdId>{
        public AdIdJavaType() {
            super(AdId.class);
        }
    }

}
