package com.example.manageskill.config;

import com.example.manageskill.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

    /*    @Autowired
    private UserRepository userRepository;*/
    @Autowired
    private CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /*    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                return org.springframework.security.core.userdetails.User
                        .withUsername(username)
                        .password(user.getPasswordHash())
                        .roles("USER")
                        .build();
            }
            throw new UsernameNotFoundException("User not found");
        }).passwordEncoder(passwordEncoder());
    }*/
    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    /*    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll() // Custom login page
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
                logoutSuccessUrl("/login")
                .permitAll() // Custom logout page
                .and()
                .formLogin().defaultSuccessUrl("/admin", true) // Redirect to admin page after successful login
                .and()
                .csrf().disable(); // Disable CSRF protection for simplicity
    }*/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                // Public: login + ALL static assets (your files are at the root like /index.css, /teams.css)
                .antMatchers(
                        "/login", "/deny", "/favicon.ico",
                        "/**/*.css", "/**/*.js",
                        "/**/*.png", "/**/*.jpg", "/**/*.jpeg", "/**/*.gif", "/**/*.svg",
                        "/**/*.woff", "/**/*.woff2", "/**/*.ttf",
                        "/webjars/**"
                ).permitAll()
                // Admin-only areas
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/members/**").hasRole("ADMIN")
                .antMatchers("/members/delete").hasRole("ADMIN")
                .antMatchers("/list-teammembers/**").hasRole("ADMIN")
                .antMatchers("/skill-groups/**").hasRole("ADMIN")
                .antMatchers("/skills/**").hasRole("ADMIN")
                .antMatchers("/member-skills/**").hasRole("ADMIN")
                // Allow team owners (ROLE_MEMBERS) to reach their team detail + member actions.
                // Controller must still check: isAdmin || isOwner(teamId)
                .antMatchers(HttpMethod.GET, "/teams/*").hasAnyRole("ADMIN", "MEMBERS")
                .antMatchers("/my-teams").hasAnyRole("ADMIN", "MEMBERS")
                .antMatchers(HttpMethod.POST, "/teams/*/members/**", "/teams/update")
                .hasAnyRole("ADMIN", "MEMBERS")
                // Everything else under /teams/** remains admin-only (list/create/delete/etc.)
                .antMatchers("/teams/**").hasRole("ADMIN")
                
                // All other requests require auth
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/deny")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
    }
}
