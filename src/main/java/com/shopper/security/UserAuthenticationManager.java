package com.shopper.security;

import com.shopper.user.ShopperUser;
import com.shopper.user.ShopperUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import static java.util.Collections.emptyList;


@Component
public class UserAuthenticationManager implements AuthenticationProvider {


    @Autowired
    ShopperUserRepository shopperUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;



    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getPrincipal().toString();
        String password = authentication.getCredentials().toString();


        ShopperUser currentUser = shopperUserRepository.findByEmail(username);
        if(currentUser == null) {
            throw new BadCredentialsException("User not found");
        }
        if(!passwordEncoder.matches(password,currentUser.getPassword())) {
            throw new BadCredentialsException("Wrong password");

        }

        return new UsernamePasswordAuthenticationToken(username,password,emptyList());



    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
