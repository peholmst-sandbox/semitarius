package net.pkhapps.semitarius.server.domain.model;

import net.pkhapps.semitarius.server.domain.AggregateRoot;
import net.pkhapps.semitarius.server.domain.ConstructorUsedByJPAOnly;
import org.jetbrains.annotations.NotNull;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

/**
 * Base class for aggregate roots that are owned by a {@link Tenant}.
 */
@MappedSuperclass
public abstract class TenantOwnedAggregateRoot extends AggregateRoot {

    protected static final String COL_TENANT = "tenant_id";

    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = COL_TENANT)
    private Tenant tenant;

    @ConstructorUsedByJPAOnly
    protected TenantOwnedAggregateRoot() {
    }

    public TenantOwnedAggregateRoot(@NotNull Tenant tenant) {
        this.tenant = Objects.requireNonNull(tenant, "tenant must not be null");
    }

    /**
     * Returns the tenant that owns this aggregate root.
     */
    @NotNull
    public Tenant getTenant() {
        return tenant;
    }
}
