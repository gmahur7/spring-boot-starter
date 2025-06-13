package com.gaurav.restApi.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.restApi.entities.UserEntity;
import com.gaurav.restApi.services.UserServices;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("users")
public class UserControllers {
    @Autowired
    private UserServices userServices;

    @GetMapping()
    public List<UserEntity> getAllUsers() {
        return userServices.getAllUser();
    }

    @PostMapping()
    public UserEntity postMethodName(@RequestBody UserEntity user) {
        return userServices.saveUser(user);
    }

    @GetMapping("{id}")
    public UserEntity getUserById(@PathVariable ObjectId id) {
        return userServices.getUserById(id).orElse(null);
    }

    @PutMapping("{id}")
    public UserEntity putMethodName(@PathVariable ObjectId id, @RequestBody UserEntity user) {
        return userServices.updateUserById(id, user);
    }

    @DeleteMapping("{id}")
    public Boolean deleteUserById(@PathVariable ObjectId id){
        return userServices.deleteUserById(id);
    }

}
