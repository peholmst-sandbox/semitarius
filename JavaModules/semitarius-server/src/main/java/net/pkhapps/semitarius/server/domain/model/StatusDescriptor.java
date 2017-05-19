package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.ConstructorUsedByJPAOnly;
import net.pkhapps.semitarius.server.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Aggregate root representing a tenant-specific status descriptor. Tenants are free to define what statuses they
 * want to use for their members.
 *
 * @see MemberStatus
 */
@Entity
@Table(name = StatusDescriptor.TABLE_NAME)
public class StatusDescriptor extends TenantOwnedAggregateRoot {

    static final String TABLE_NAME = "status_descriptors";
    static final String COL_NAME = "name";
    static final String COL_COLOR = "color";

    @Column(nullable = false, name = COL_NAME)
    private String name;

    @Column(name = COL_COLOR)
    private Integer color;

    @ConstructorUsedByJPAOnly
    @SuppressWarnings("unused")
    StatusDescriptor() {
    }

    public StatusDescriptor(@NotNull Tenant tenant, @NotNull String name) {
        super(tenant);
        this.name = Strings.requireNonEmpty(name, "name must not be empty");
    }

    /**
     * Returns the name of this status descriptor. The name will show up in UIs etc.
     */
    @NotNull
    public String getName() {
        return name;
    }

    /**
     * Renames this status descriptor, firing a {@link StatusDescriptorRenamed} domain event.
     */
    public void rename(@NotNull String name) {
        this.name = Strings.requireNonEmpty(name, "name must not be empty");
        getId().ifPresent(id -> registerEvent(new StatusDescriptorRenamed(id, name)));
    }

    /**
     * Returns the color of this status descriptor as an RGB integer, if set.
     */
    @Nullable
    public Integer getColor() {
        return color;
    }

    /**
     * Changes the color of this status descriptor, firing a {@link StatusDescriptorColorChanged} domain event.
     */
    public void changeColor(@Nullable Integer color) {
        this.color = color;
        getId().ifPresent(id -> registerEvent(new StatusDescriptorColorChanged(id, color)));
    }
}
