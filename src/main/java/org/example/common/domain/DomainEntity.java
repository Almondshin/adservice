package org.example.common.domain;

public abstract class DomainEntity<T extends DomainEntity<T, TID>, TID> {
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        return equals((T) other);
    }

    public boolean equals(T other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        if (getId() == null || other.getId() == null) {
            return false;
        }

        return getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getId() == null ? 0 : getId().hashCode();
    }

    abstract public TID getId();
}