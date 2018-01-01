package mba.patronage.vehicles.model.view;

import lombok.Getter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Entity
@Getter
public class VehicleView {

    @Id
    @GeneratedValue
    private UUID uuid;
    @NotNull
    @NotEmpty
    private String brand;
    @NotEmpty
    @NotNull
    private String model;
    @NotEmpty
    @NotNull
    private String vin;

}
