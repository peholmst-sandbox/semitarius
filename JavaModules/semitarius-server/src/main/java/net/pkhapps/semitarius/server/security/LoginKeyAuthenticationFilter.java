package net.pkhapps.semitarius.server.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Authentication filter that supports "login key authentication". This is a kind of "remember me" authentication,
 * but designed for REST APIs that don't use cookies. The filter will check if the HTTP request contains a special
 * header with a login key and if so, create a new {@link LoginKeyAuthenticationToken} and authenticate using that.
 * If authentication succeeds, the current
 * {@link org.springframework.security.core.context.SecurityContext security context} is
 * updated. If it fails, the filter will proceed down the chain, allowing other authentication filters to process the
 * request. This filter will not even try to perform any authentication if the current security context already
 * contains a valid authentication token.
 *
 * @see LoginKeyAuthenticationProvider
 */
class LoginKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginKeyAuthenticationFilter.class);

    private static final String LOGIN_KEY_HEADER_NAME = "semitarius-login-key";

    private final AuthenticationManager authenticationManager;

    LoginKeyAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String loginKey = httpServletRequest.getHeader(LOGIN_KEY_HEADER_NAME);
        final Authentication currentAuthentication = SecurityContextHolder.getContext().getAuthentication();
        if (loginKey != null && (currentAuthentication == null || !currentAuthentication.isAuthenticated())) {
            LOGGER.debug("Found login key header");
            LoginKeyAuthenticationToken token = new LoginKeyAuthenticationToken(loginKey);
            try {
                final Authentication authResult = authenticationManager.authenticate(token);
                LOGGER.debug("Login key was correct, authenticating request");
                SecurityContextHolder.getContext().setAuthentication(authResult);
            } catch (AuthenticationException ex) {
                LOGGER.debug("Login key was incorrect, clearing security context");
                SecurityContextHolder.clearContext();
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
