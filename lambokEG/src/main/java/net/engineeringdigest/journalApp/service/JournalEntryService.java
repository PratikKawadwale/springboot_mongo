package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.journalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    private journalEntryRepository journalEntryRepository;

    @Autowired
    private  UserService userService;

    @Transactional //function madhe sagla code proper run zala tr sagla run hoil ani jr code madhe kuthe error ali tr tya adhicha run zalela code rollback hoil
    public void saveEntry(JournalEntry entry, String userName){
       try {
           User user=userService.findByUserName(userName);
           entry.setDate(LocalDateTime.now());
          JournalEntry saved= journalEntryRepository.save(entry);
           user.getJournalEntries().add(saved);
//           user.setUserName(null);  error yenar
           userService.saveEntry(user);
       } catch (Exception e) {
           throw new RuntimeException(e);
       }

    }
    public void saveEntry(JournalEntry entry){  //method overload for putmapping
        journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getAll(){

        return journalEntryRepository.findAll(); //display values in postman
    }

    public Optional<JournalEntry> findById(ObjectId id){

        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id, String userName){
        User user=userService.findByUserName(userName);
        user.getJournalEntries().removeIf(X -> X.getId().equals(id));//user database madun ti entry delete karnyasathi
        userService.saveEntry(user);
        journalEntryRepository.deleteById(id);
    }
}
