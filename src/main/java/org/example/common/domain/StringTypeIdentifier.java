package org.example.common.domain;

public abstract class StringTypeIdentifier extends ValueObject<StringTypeIdentifier> {
    private String id;

    public StringTypeIdentifier(String id) {
        this.id = id;
    }

    public String stringValue() {
        return id;
    }

    @Override
    protected Object[] getEqualityFields() {
        return new Object[] { id };
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " : " + stringValue();
    }
}