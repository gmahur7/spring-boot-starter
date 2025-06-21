package com.gaurav.restApi.services;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

  public ResponseEntity<List<JournalEntity>> getAllJournals(ObjectId userId) {
    try {
      UserEntity user = userRepo.findById(userId).orElse(null);
      if (user == null) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }

      List<JournalEntity> entries = journalRepo.findByUserId(userId);
      if (entries.size() == 0) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(entries, HttpStatus.OK);
    } catch (Exception error) {
      System.out.println("Error in getting all journals: " + error);
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @Transactional
  public ResponseEntity<?> createEntry(JournalEntity entry, ObjectId userId) {
    try {
      UserEntity user = userRepo.findById(userId).orElse(null);
      if (user == null) {
        return new ResponseEntity<>("User not found", HttpStatus.BAD_REQUEST);
      }
      JournalEntity alreadyEntry = this.journalRepo.getByTitle(entry.getTitle());
      if (alreadyEntry != null) {
        return new ResponseEntity<>("Entry already exist with title", HttpStatus.BAD_REQUEST);
      }
      entry.setCreatedAt(LocalDateTime.now());
      entry.setUpdatedAt(null);
      entry.setUser(user);
      JournalEntity savedEntry = this.journalRepo.save(entry);
      return new ResponseEntity<>(savedEntry, HttpStatus.CREATED);
    } catch (Exception e) {
      System.out.println("Error in saving new journal: " + e);
      return new ResponseEntity<>("Error in saving new journal entry", HttpStatus.BAD_REQUEST);
    }
  }

  @Transactional
  public ResponseEntity<JournalEntity> updateById(ObjectId id, JournalEntity entity) {
    try {
      JournalEntity oldEntry = journalRepo.findById(id).orElse(null);
      if (oldEntry == null) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      if (entity.getTitle() != null) {
        oldEntry.setTitle(entity.getTitle());
      }
      if (entity.getContent() != null) {
        oldEntry.setContent(entity.getContent());
      }
      oldEntry.setUpdatedAt(LocalDateTime.now());
      JournalEntity updatedEntry = journalRepo.save(oldEntry);
      return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Error in updating journal entry: " + e);
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  @Transactional
  public ResponseEntity<?> deleteAllJournalsByUserId(ObjectId userId) {
    try {
      UserEntity user = userRepo.findById(userId).orElse(null);
      if (user == null) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
      }
      List<JournalEntity> entries = journalRepo.findByUserId(userId);
      if (entries.size() == 0) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      journalRepo.deleteAll(entries);
      return new ResponseEntity<>("All journals deleted successfully", HttpStatus.OK);
    } catch (Exception error) {
      System.out.println("Error in deleting all journals: " + error);
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

  }

  @Transactional
  public Boolean deleteAllJournalsByUserId(UserEntity user) {
    try {
      List<JournalEntity> entries = journalRepo.findByUserId(user.getId());
      if (entries.size() == 0) {
        return true;
      }
      journalRepo.deleteAll(entries);
      return true;
    } catch (Exception error) {
      System.out.println("Error in deleting all journals: " + error);
      return false;
    }
  }

  public ResponseEntity<JournalEntity> getEntryById(ObjectId id) {
    try {
      JournalEntity entry = journalRepo.findById(id).orElse(null);
      if (entry == null) {
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
      }
      return new ResponseEntity<>(entry, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Error in getting journal entry by ID: " + e);
      return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }
  }

  public ResponseEntity<String> deleteById(ObjectId id) {
    try {
      JournalEntity entry = journalRepo.findById(id).orElse(null);
      if (entry == null) {
        return new ResponseEntity<>("Journal entry not found", HttpStatus.NOT_FOUND);
      }
      journalRepo.delete(entry);
      return new ResponseEntity<>("Journal entry deleted successfully", HttpStatus.OK);
    } catch (Exception e) {
      System.out.println("Error in deleting journal entry: " + e);
      return new ResponseEntity<>("Error in deleting journal entry", HttpStatus.BAD_REQUEST);
    }
  }
}
