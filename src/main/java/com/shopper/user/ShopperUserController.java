package com.shopper.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/user")
public class ShopperUserController {


    @Autowired
    ShopperUserService userService;

    @RequestMapping(value = "signup", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<ShopperUser> signUp(@ApiIgnore WebRequest webRequest, @RequestBody ShopperUser user) {
        ShopperUser shopperUser = userService.signUp(user);

        if(shopperUser != null) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(null);
        } else {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .body(null);
        }
    }

}
