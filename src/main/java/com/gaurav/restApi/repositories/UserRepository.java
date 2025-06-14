package com.gaurav.restApi.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.gaurav.restApi.entities.UserEntity;

public interface UserRepository extends MongoRepository<UserEntity,ObjectId> {
    UserEntity findByEmail(String email);
} 
