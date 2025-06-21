package com.gaurav.restApi.entities;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;

@Document(collection = "journal")
@Data
public class JournalEntity {
    @Id
    private ObjectId id;
    @DBRef
    private UserEntity user;
    @NonNull
    private String title;
    @NonNull
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
