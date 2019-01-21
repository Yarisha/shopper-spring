package com.shopper.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@Component
public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    UserAuthenticationManager userAuthenticationManager;


    public UserAuthenticationFilter() {
        super("/authentication/login");
    }

    @Override
    public void afterPropertiesSet() {
        // ignore because we are using out own authentication manager.
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {

        try {
            return userAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken(httpServletRequest.getHeader("email"),httpServletRequest.getHeader("password"),new ArrayList<>()));
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, Authentication authentication) throws ServletException {

        Date now = new Date();
        Date expiration = new Date(System.currentTimeMillis() + 864_000_000);

        String token = JWT.create()
                .withSubject(authentication.getPrincipal().toString())
                .withExpiresAt(expiration)
                .sign(Algorithm.HMAC512("SHOPPER_SECRET".getBytes()));

        response.addHeader("AUTH_TOKEN","Bearer "+ token);
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failedAuthentication) throws ServletException, IOException {

        super.unsuccessfulAuthentication(request,response,failedAuthentication);
    }
}
