package com.gaurav.restApi.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
// import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection = "users")
@Data
public class UserEntity {
    @Id
    private ObjectId id;
    private String name;
    // @Indexed(unique = true)
    @NonNull
    private String email;
    @NonNull
    private String password;
    private int age;
    private LocalDateTime date;
    private LocalDateTime updatedAt;
}
