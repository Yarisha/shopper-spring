package com.shopper.user;

import com.shopper.user.ShopperUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopperUserRepository extends JpaRepository<ShopperUser, Long> {

    ShopperUser findByEmail(String email);
}
