package mba.patronage.clients.model.db;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
public class Client {

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
