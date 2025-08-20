// LoginFailureHandler.java
package com.example.manageskill.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest req,
                                        HttpServletResponse res,
                                        AuthenticationException ex)
            throws IOException, ServletException {

        String url = "/login?error";

        if (ex instanceof UsernameNotFoundException) {
            url += "&uerr=notfound";        // email/username not found
        } else if (ex instanceof BadCredentialsException) {
            url += "&perr=bad";             // wrong password
        } else {
            url += "&gen=1";                // generic error
        }
        getRedirectStrategy().sendRedirect(req, res, url);
    }
}
