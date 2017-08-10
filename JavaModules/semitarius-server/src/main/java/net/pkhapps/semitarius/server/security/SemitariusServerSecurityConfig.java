package net.pkhapps.semitarius.server.security;

import net.pkhapps.semitarius.server.domain.DeviceAccountRepository;
import net.pkhapps.semitarius.server.domain.UserAccountRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * Security configuration for the Semitarius server application. We are using filter based security for
 * authentication only.
 * Authorization is handled by custom aspects that are aware of tenants and members.
 */
@Configuration
@EnableWebSecurity
class SemitariusServerSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        final LoginKeyAuthenticationFilter loginKeyAuthenticationFilter =
                new LoginKeyAuthenticationFilter(authenticationManager());
        http.httpBasic().realmName("Semitarius Server").and().addFilterAfter(loginKeyAuthenticationFilter,
                BasicAuthenticationFilter.class).sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().csrf().disable().authorizeRequests()
                .antMatchers("/api/**").fullyAuthenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final UserDetailsService userDetailsService =
                new UserAccountDetailsService(getApplicationContext().getBean(UserAccountRepository.class));
        final AuthenticationProvider authenticationProvider =
                new LoginKeyAuthenticationProvider(getApplicationContext().getBean(
                        DeviceAccountRepository.class));
        auth.userDetailsService(userDetailsService).and().authenticationProvider(authenticationProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }
}
