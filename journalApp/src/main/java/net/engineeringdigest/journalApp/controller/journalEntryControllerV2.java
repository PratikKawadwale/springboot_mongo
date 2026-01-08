package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.journalEntry;
import net.engineeringdigest.journalApp.service.journalEntryService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class journalEntryControllerV2 {
    private static final Logger log = LoggerFactory.getLogger(journalEntryControllerV2.class);

    @Autowired
    private journalEntryService journalentryservice;
    @GetMapping
    public List<journalEntry> getAll(){

        return journalentryservice.getAll();
    }

    @PostMapping
    public journalEntry createEntry(@RequestBody journalEntry myEntry){
        myEntry.setDate(LocalDateTime.now());
        journalentryservice.saveEntry(myEntry);
        return myEntry;
    }
    @GetMapping("id/{myId}")
    public journalEntry getJounralEntryById(@PathVariable ObjectId myId){
        return journalentryservice.findById(myId).orElse(null);

    }
   @DeleteMapping("id/{myId}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId myId){
       journalentryservice.deleteById(myId);
       return true;

   }
   @PutMapping("id/{Id}")
    public journalEntry updateJournalById(@PathVariable ObjectId Id, @RequestBody journalEntry newEntry) {
       journalEntry old= journalentryservice.findById(Id).orElse(null);
         if(old!=null)
         {
             old.setTitle(newEntry.getTitle()!= null && !newEntry.getTitle().equals("") ? newEntry.getTitle(): old.getTitle());
             old.setContent(newEntry.getContent() !=null && newEntry.equals("") ? newEntry.getContent() : old.getContent());
         }
         journalentryservice.saveEntry(old);
         return old;
   }

}
