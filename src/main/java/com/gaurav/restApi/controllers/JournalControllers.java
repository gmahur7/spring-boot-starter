package com.gaurav.restApi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gaurav.restApi.entities.JournalEntity;
import com.gaurav.restApi.services.JournalServices;

@RestController
@RequestMapping("journals")
public class JournalControllers {
    @Autowired
    private JournalServices journalServices;
    
    @GetMapping()
    public ResponseEntity<List<JournalEntity>> getAllEntries(){
        return journalServices.getAllJournals();
    }

    @PostMapping()
    public ResponseEntity<JournalEntity> createEntry(@RequestBody JournalEntity entry){
        return journalServices.createEntry(entry);
    }
}
