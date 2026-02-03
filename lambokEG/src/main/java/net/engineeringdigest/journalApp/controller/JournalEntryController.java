package net.engineeringdigest.journalApp.controller;
import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private static final Logger log = LoggerFactory.getLogger(JournalEntryController.class);

    @Autowired
    private JournalEntryService journalentryservice;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName){
        User user=userService.findByUserName(userName);
        List<JournalEntry>all=user.getJournalEntries();
        if(all!=null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(all, HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,
                                                    @PathVariable String userName){
        try {
            User user=userService.findByUserName(userName);
            journalentryservice.saveEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("id/{myId}")
    public ResponseEntity<?> getJounralEntryById(@PathVariable ObjectId myId){
        Optional<JournalEntry> journalEntry=journalentryservice.findById(myId);
        if(journalEntry.isPresent()){
            return new ResponseEntity<>(journalEntry.get(),HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("id/{userName}/{myId}")//for mongodb
    public ResponseEntity<JournalEntry> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName){
        journalentryservice.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
    @PutMapping("id/{userName}/{Id}")
    public JournalEntry updateJournalById(@PathVariable ObjectId Id,
                                          @RequestBody JournalEntry newEntry,
                                          @PathVariable String userName) {
        JournalEntry old= journalentryservice.findById(Id).orElse(null);
        if(old!=null)
        {
            old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
            old.setContent(newEntry.getContent() !=null && newEntry.equals("") ? newEntry.getContent() : old.getContent());
        }
        journalentryservice.saveEntry(old);
        return old;
    }

}

