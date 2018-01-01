package mba.patronage.vehicles;

import mba.patronage.exception.NotFoundException;
import mba.patronage.vehicles.model.db.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    @Autowired
    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    List<Vehicle> findAll() {
        return vehicleRepository.findAll();
    }

    Vehicle findById(UUID id) throws NotFoundException {
        throwVehicleNotFoundIfDoesNotExist(id, Vehicle.class);
        return vehicleRepository.findOne(id);
    }

    Vehicle create(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    private void throwVehicleNotFoundIfDoesNotExist(UUID id, Class object) throws NotFoundException {
        if (!vehicleRepository.exists(id)) {
            throw new NotFoundException(object.getSimpleName());
        }
    }
}
