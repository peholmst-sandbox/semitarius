package net.pkhapps.semitarius.server.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.util.ClassUtils;

import javax.persistence.*;
import java.util.Objects;
import java.util.Optional;

/**
 * Base class for aggregate roots.
 */
@MappedSuperclass
public abstract class AggregateRoot extends AbstractAggregateRoot {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Version
    @Column(name = "version", nullable = false)
    private Long version;

    /**
     * Returns the ID of this aggregate root.
     */
    @NotNull
    @Transient
    public Optional<Long> getId() {
        return Optional.ofNullable(id);
    }

    /**
     * Returns the ID of this aggregate root or throws an exception if no ID has been assigned yet.
     */
    @NotNull
    public Long requireId() {
        return getId().orElseThrow(() -> new IllegalStateException("Aggregate root has no ID yet"));
    }

    /**
     * Sets the ID of this aggregate root. Should normally never be called since the ID is auto-generated.
     */
    protected void setId(@Nullable Long id) {
        this.id = id;
    }

    /**
     * Returns the optimistic locking version number of this aggregate root.
     */
    @NotNull
    @Transient
    public Optional<Long> getVersion() {
        return Optional.ofNullable(version);
    }

    /**
     * Sets the optimistic locking version number of this aggregate root. Should normally never be called since the
     * version is automatically set by JPA.
     */
    protected void setVersion(@Nullable Long version) {
        this.version = version;
    }

    /**
     * Returns whether this aggregate root is new (meaning it has no ID) or persisted.
     */
    @Transient
    public boolean isNew() {
        return id == null;
    }

    @Override
    public boolean equals(Object obj) {
        if (null == obj) {
            return false;
        } else if (this == obj) {
            return true;
        } else if (!this.getClass().equals(ClassUtils.getUserClass(obj))) {
            return false;
        } else {
            AggregateRoot that = (AggregateRoot) obj;
            return null != this.id && this.id.equals(that.id);
        }
    }

    @Override
    public int hashCode() {
        return id == null ? super.hashCode() : Objects.hash(getClass(), id);
    }
}
