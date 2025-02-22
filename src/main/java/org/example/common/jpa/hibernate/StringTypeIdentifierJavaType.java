package org.example.common.jpa.hibernate;

import org.example.common.domain.StringTypeIdentifier;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.StringType;
import org.hibernate.usertype.UserType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public abstract class StringTypeIdentifierJavaType<T extends StringTypeIdentifier> implements UserType {

    private final Class<T> clazz;

    protected StringTypeIdentifierJavaType(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class<T> returnedClass() {
        return clazz;
    }

    @Override
    public T nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor session, Object owner) throws HibernateException, SQLException {
        String value = (String) StringType.INSTANCE.nullSafeGet(rs, names, session, owner);
        if (value == null) {
            return null;
        }
        try {
            return clazz.getDeclaredConstructor(String.class).newInstance(value);
        } catch (Exception ex) {
            throw new HibernateException("Failed to instantiate " + clazz.getName(), ex);
        }
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            StringType.INSTANCE.nullSafeSet(st, null, index, session);
        } else {
            StringType.INSTANCE.nullSafeSet(st, ((StringTypeIdentifier) value).stringValue(), index, session);
        }
    }

}
