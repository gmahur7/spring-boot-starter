package com.gaurav.restApi.services;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.gaurav.restApi.entities.UserEntity;
import com.gaurav.restApi.repositories.UserRepository;

@Component
public class UserServices {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JournalServices journalServices;

    public ResponseEntity<?> saveUser(UserEntity user) {
        try {
            UserEntity isExistWithEmail = userRepository.findByEmail(user.getEmail());
            if(isExistWithEmail!=null){
               return new ResponseEntity<>("User already exist with given email",HttpStatus.BAD_REQUEST);            
            }
            user.setDate(LocalDateTime.now());
            userRepository.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception error) {
            System.out.println("Error in saving user: " + error);
            return new ResponseEntity<>(error.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<UserEntity>> getAllUser() {
        try {
            List<UserEntity> users = userRepository.findAll();
            if (users.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<UserEntity> getUserById(ObjectId id) {
       try {
         UserEntity user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return new ResponseEntity<>(user, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
       } catch (Exception e) {
         return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }

    public ResponseEntity<UserEntity> updateUserById(ObjectId id, UserEntity user) {
        try {
            UserEntity oldUser = userRepository.findById(id).orElse(null);
            if (oldUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            if (user.getName() != null && !oldUser.getName().equals(user.getName())) {
                oldUser.setName(user.getName());
            }
            if (user.getEmail() != null && !oldUser.getEmail().equals(user.getEmail())) {
                oldUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null && !oldUser.getPassword().equals(user.getPassword())) {
                oldUser.setPassword(user.getPassword());
            }
            if (user.getAge() != 0 && user.getAge() != oldUser.getAge()) {
                oldUser.setAge(user.getAge());
            }
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(oldUser);
            return new ResponseEntity<>(oldUser, HttpStatus.OK);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    public ResponseEntity<?> deleteUserById(ObjectId id) {
        try {
            UserEntity user = userRepository.findById(id).orElse(null);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            boolean response = journalServices.deleteAllJournalsByUserId(user);
            if(!response) {
                return new ResponseEntity<>("Error in deleting journals", HttpStatus.BAD_REQUEST);
            }
            
            userRepository.deleteById(id);
            return new ResponseEntity<>("User Deleted Successfully",HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.out.println("Error in deleting user: " + e);
            return new ResponseEntity<>("Error in deleteing user",HttpStatus.BAD_REQUEST);
        }
    }
}
