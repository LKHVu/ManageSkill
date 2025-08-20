package com.example.manageskill.config;

import com.example.manageskill.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(encoder());
        p.setHideUserNotFoundExceptions(false);
        return p;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                // Public endpoints + static assets
                .antMatchers(
                        "/login", "/deny", "/favicon.ico",
                        "/**/*.css", "/**/*.js",
                        "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/*.svg",
                        "/**/*.woff", "/**/*.woff2", "/**/*.ttf",
                        "/webjars/**"
                ).permitAll()
                // Admin-only
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/members/**").hasRole("ADMIN")
                .antMatchers("/members/delete").hasRole("ADMIN")
                .antMatchers("/list-teammembers/**").hasRole("ADMIN")
                .antMatchers("/skill-groups/**").hasRole("ADMIN")
                .antMatchers("/skills/**").hasRole("ADMIN")
                .antMatchers("/member-skills/**").hasRole("ADMIN")
                // Owners (ROLE_MEMBERS) can view team detail
                .antMatchers(HttpMethod.GET, "/teams/*").hasAnyRole("ADMIN", "MEMBERS")
                .antMatchers("/my-teams").hasAnyRole("ADMIN", "MEMBERS")
                .antMatchers(HttpMethod.POST, "/teams/*/members/**", "/teams/update")
                .hasAnyRole("ADMIN", "MEMBERS")
                // Everything else under /teams/** remains admin-only
                .antMatchers("/teams/**").hasRole("ADMIN")
                // All other requests require auth
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/deny")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .usernameParameter("username") // single field (email or username)
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureHandler(loginFailureHandler)
                .permitAll()
                .and()
                .rememberMe()
                .rememberMeParameter("remember-me") // must match checkbox name
                .tokenValiditySeconds(24 * 60 * 60) // 24h
                .key("aVeryLongRandomSecretThatStaysTheSameForever123!@#") // <--- important
                .userDetailsService(userDetailsService)
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("JSESSIONID", "remember-me")
                .permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint);
    }
}
