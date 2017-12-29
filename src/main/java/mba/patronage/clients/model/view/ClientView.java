package mba.patronage.clients.model.view;

import lombok.Getter;
import mba.patronage.clients.model.db.Sex;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
public class ClientView {

    @Id
    @GeneratedValue
    private UUID uuid;
    private String surname;
    @ElementCollection
    private Set<String> names;
    private Date dateOfBirth;
    private Sex sex;
    private String pid;
}
