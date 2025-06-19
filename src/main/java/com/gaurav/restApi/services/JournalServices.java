package com.gaurav.restApi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.gaurav.restApi.entities.JournalEntity;
import com.gaurav.restApi.entities.UserEntity;
import com.gaurav.restApi.repositories.JournalRepository;
import com.gaurav.restApi.repositories.UserRepository;

@Component
public class JournalServices {
  @Autowired
  private JournalRepository journalRepo;  
  @Autowired
  private UserRepository userRepo;

  public ResponseEntity<List<JournalEntity>> getAllJournals(){
    try {
        List<JournalEntity> entries = this.journalRepo.findAll();
        if(entries.size()==0){
         return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entries,HttpStatus.OK);
    } catch (Exception error) {
        System.out.println("Error in getting all journals: "+error);
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<JournalEntity> createEntry(JournalEntity entry){
    try {
        JournalEntity alreadyEntry = this.journalRepo.getByTitle(entry.getTitle());
        if(alreadyEntry!=null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        UserEntity user = userRepo.findById(entry.getUser().getId()).orElse(null);
        if(user==null){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }
        JournalEntity savedEntry =  this.journalRepo.save(entry);
        return new ResponseEntity<>(savedEntry,HttpStatus.CREATED);
    } catch (Exception e) {
          System.out.println("Error in saving new journal: "+e);
         return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
  }
}
