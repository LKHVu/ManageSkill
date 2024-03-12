package com.example.manageskill.config;

import com.example.manageskill.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        http
                .csrf().disable();

        http.authorizeRequests().antMatchers("/login").permitAll();

        http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/teams/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/members/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/list-teammembers/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/skill-groups/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/skills/**").hasRole("ADMIN");
        http.authorizeRequests().antMatchers("/member-skills/**").hasRole("ADMIN");
        // thiết lập action trả về khi truy cập trang không có quyền
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/deny");

        http.authorizeRequests().and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        http
                .rememberMe().tokenValiditySeconds(60 * 60 * 24 * 1); // 1 day
    }
}