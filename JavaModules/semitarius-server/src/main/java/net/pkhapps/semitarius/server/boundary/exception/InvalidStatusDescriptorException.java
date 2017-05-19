package net.pkhapps.semitarius.server.boundary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown by a REST boundary when a status descriptor is invalid.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid status descriptor")
public class InvalidStatusDescriptorException extends RuntimeException {
}
