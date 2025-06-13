package com.gaurav.restApi.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gaurav.restApi.entities.UserEntity;
import com.gaurav.restApi.repositories.UserRepository;

@Component
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    public UserEntity saveUser(UserEntity user){
        try{
            user.setDate(LocalDate.now());
            return userRepository.save(user);
        }catch(Exception error){
            System.out.println("Error in saving user: "+error);
            return null;
        }
    }

    public List<UserEntity> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<UserEntity> getUserById(ObjectId id){
        return userRepository.findById(id);
    }

    public UserEntity updateUserById(ObjectId id, UserEntity user){
        UserEntity oldUser = userRepository.findById(id).orElse(null);
        if(oldUser!=null){
            if (user.getName() != null && !oldUser.getName().equals(user.getName())) {
                oldUser.setName(user.getName());
            }
            if(user.getEmail()!=null && !oldUser.getEmail().equals(user.getEmail())){
                oldUser.setEmail(user.getEmail());
            }
            if(user.getPassword()!=null && !oldUser.getPassword().equals(user.getPassword())){
                oldUser.setPassword(user.getPassword());
            }
            if(user.getAge() !=0 && user.getAge()!=oldUser.getAge()){
                oldUser.setAge(user.getAge());
            }
        }
        userRepository.save(oldUser);
        return oldUser;
    }

    public boolean deleteUserById(ObjectId id){
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
