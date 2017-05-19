package net.pkhapps.semitarius.server.boundary.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown by a REST boundary when a member cannot be found.
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Member not found")
public class MemberNotFoundException extends RuntimeException {
}
