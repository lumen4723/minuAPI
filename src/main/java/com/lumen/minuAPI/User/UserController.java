package com.lumen.minuAPI.User;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @GetMapping
    public String getUserList() {
        return new String();
    }

    @GetMapping("/{id}")
    public String getUserInfo(
        @PathVariable String id
    ) {
        return new String();
    }
    
    @PostMapping
    public String createUser(
        @RequestBody String entity
    ) {
        //TODO: process POST request
        
        return entity;
    }
    
    @PutMapping("/{id}")
    public String updateUserInfo(
        @PathVariable String id,
        @RequestBody String entity
    ) {
        //TODO: process PUT request
        
        return entity;
    }

    @DeleteMapping("/{id}")
    public String DeleteUser(
        @PathVariable String id
    ) {
        return new String();
    }

}
