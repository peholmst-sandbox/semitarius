package net.pkhapps.semitarius.server.boundary.converters;

import net.pkhapps.semitarius.server.boundary.exception.TenantNotFoundException;
import net.pkhapps.semitarius.server.domain.Tenant;
import net.pkhapps.semitarius.server.domain.TenantRepository;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * A Spring converter that converts a string (the {@link Tenant#getIdentifier() tenant identifier}) to a
 * {@link Tenant}. If the tenant is not found, a {@link TenantNotFoundException} is thrown. In other words,
 * a REST method that takes a {@link Tenant} as a parameter will never be called with the parameter being {@code null}.
 */
@Component
class TenantConverter implements Converter<String, Tenant> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantConverter.class);

    private final TenantRepository tenantRepository;

    @Autowired
    TenantConverter(@NotNull TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Override
    public Tenant convert(String source) {
        final Tenant tenant = tenantRepository.findByIdentifier(source).orElseThrow(TenantNotFoundException::new);
        LOGGER.trace("Converted [{}] to tenant [{}]", source, tenant);
        return tenant;
    }
}
