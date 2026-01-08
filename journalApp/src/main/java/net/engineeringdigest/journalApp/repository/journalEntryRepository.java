package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
//MongoRepository sagl handle krt
public interface journalEntryRepository extends MongoRepository<journalEntry, ObjectId>{

}
