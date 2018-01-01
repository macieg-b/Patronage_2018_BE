package mba.patronage;

import mba.patronage.clients.ClientRepository;
import mba.patronage.clients.model.db.Client;
import mba.patronage.clients.model.db.Sex;
import mba.patronage.vehicles.VehicleRepository;
import mba.patronage.vehicles.model.db.Vehicle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;

@SpringBootApplication
public class PatronageBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatronageBeApplication.class, args);
    }

    @Bean
    CommandLineRunner initClients(ClientRepository clientRepository) {
        return (args) -> {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            HashSet<String> men = new HashSet<>();
            men.add("Maciej");
            men.add("Bartek");

            HashSet<String> women = new HashSet<>();
            women.add("Joanna");

            clientRepository.save(Client.builder()
                    .names(men)
                    .surname("Kowalski")
                    .pid("94011144789")
                    .dateOfBirth(dateFormat.parse("01/01/1994"))
                    .sex(Sex.MALE)
                    .build());
            clientRepository.save(Client.builder()
                    .names(women)
                    .surname("Kowalska")
                    .pid("92011144789")
                    .dateOfBirth(dateFormat.parse("01/01/1992"))
                    .sex(Sex.FEMALE)
                    .build());
            clientRepository.save(Client.builder()
                    .names(women)
                    .surname("Janowska")
                    .pid("90011144789")
                    .dateOfBirth(dateFormat.parse("02/01/1990"))
                    .sex(Sex.FEMALE)
                    .build());



        };
    }

    @Bean
    CommandLineRunner initVehicles(VehicleRepository vehicleRepository) {
        return (args) -> {
            vehicleRepository.save(Vehicle.builder()
                    .brand("Fiat")
                    .model("500")
                    .vin("ABC123456789DEF")
                    .build());

            vehicleRepository.save(Vehicle.builder()
                    .brand("Jeep")
                    .model("Wrangler")
                    .vin("DEF123456789ABC")
                    .build());

        };
    }
}

