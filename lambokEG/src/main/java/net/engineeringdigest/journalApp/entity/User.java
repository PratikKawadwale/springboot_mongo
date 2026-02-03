package net.engineeringdigest.journalApp.entity;

import lombok.Data;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document(collection="users")
@Data  //by defoult getter seteer and other property genrate karto
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)  //unique username sathi ani application.properties madhe pn auto-index-creation true karava lagto
    @NonNull  //null nahi pahije  sererr set karaychya veles check karto
    private String userName;
    @NonNull
    private String password;

    @DBRef //refernce mhanun dusry collections chi id deto
    private List<JournalEntry> journalEntries=new ArrayList<>();

}
