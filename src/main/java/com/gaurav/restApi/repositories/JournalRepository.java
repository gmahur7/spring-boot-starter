package com.gaurav.restApi.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.gaurav.restApi.entities.JournalEntity;

public interface JournalRepository extends MongoRepository<JournalEntity,ObjectId>{
    JournalEntity getByTitle(String title);
}
