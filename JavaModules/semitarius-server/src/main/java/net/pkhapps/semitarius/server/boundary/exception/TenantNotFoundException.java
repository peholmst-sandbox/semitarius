package net.pkhapps.semitarius.server.boundary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown by a REST boundary when a tenant cannot be found.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Tenant not found")
public class TenantNotFoundException extends RuntimeException {
}
