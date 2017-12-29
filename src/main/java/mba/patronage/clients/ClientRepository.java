package mba.patronage.clients;

import mba.patronage.clients.model.db.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository <Client, UUID> {
}
