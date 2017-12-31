package mba.patronage.clients.model.view;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import mba.patronage.clients.model.db.Sex;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
public class ClientView {

    @Id
    @GeneratedValue
    private UUID uuid;
    @NotNull
    private String surname;
    @ElementCollection
    @NotEmpty
    private Set<String> names;
    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private Sex sex;
    @NotNull
    private String pid;
}
