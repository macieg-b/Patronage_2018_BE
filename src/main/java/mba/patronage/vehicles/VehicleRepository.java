package mba.patronage.vehicles;

import mba.patronage.vehicles.model.db.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository <Vehicle, UUID> {
}
