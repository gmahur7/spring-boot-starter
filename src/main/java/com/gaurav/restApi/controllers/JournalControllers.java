package com.gaurav.restApi.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.restApi.entities.JournalEntity;
import com.gaurav.restApi.services.JournalServices;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("journals")
public class JournalControllers {
    @Autowired
    private JournalServices journalServices;
    
    @GetMapping("{userId}")
    public ResponseEntity<List<JournalEntity>> getAllEntries(@PathVariable ObjectId userId){
        return journalServices.getAllJournals(userId);
    }

    @PostMapping("{userId}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntity entry, @PathVariable ObjectId userId){
        return journalServices.createEntry(entry,userId);
    }

    @PutMapping("{id}")
    public ResponseEntity<JournalEntity> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntity entity) {
       return journalServices.updateById(id,entity);
    }

    @GetMapping("entry/{id}")
    public ResponseEntity<JournalEntity> getEntryById(@PathVariable ObjectId id) {
        return journalServices.getEntryById(id);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<String> deleteEntryById(@PathVariable ObjectId id) {
        return journalServices.deleteById(id);
    }

    @DeleteMapping("deleteAll/{userId}")
    public ResponseEntity<?> deleteAllEntries(@PathVariable ObjectId userId) {
        return journalServices.deleteAllJournalsByUserId(userId);
    }
}
