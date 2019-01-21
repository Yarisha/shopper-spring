package com.shopper.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ShopperUserService {

    @Autowired
    ShopperUserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional
    public ShopperUser signUp(ShopperUser user) {

        ShopperUser shopperUser = userRepository.findByEmail(user.getEmail());
        ShopperUser newUser = null;
        if(shopperUser == null) {

            newUser = new ShopperUser();
            newUser.setEmail(user.getEmail());
            newUser.setFirst_name(user.getFirst_name());
            newUser.setLast_name(user.getLast_name());
            newUser.setPassword(passwordEncoder.encode(user.getPassword()));
            System.out.println(newUser.getPassword().length());
            userRepository.save(newUser);

        }

        return newUser;
    }

}
