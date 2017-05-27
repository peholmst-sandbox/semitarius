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
 * TODO Document me
 */
class LoginKeyAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginKeyAuthenticationFilter.class);

    static final String LOGIN_KEY_HEADER_NAME = "semitarius-login-key";

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
