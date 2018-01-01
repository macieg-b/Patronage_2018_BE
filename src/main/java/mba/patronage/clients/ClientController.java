package mba.patronage.clients;

import javassist.NotFoundException;
import mba.patronage.clients.model.db.Client;
import mba.patronage.clients.model.view.ClientView;
import mba.patronage.config.Api;
import mba.patronage.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = Api.URL_CLIENT, produces = MediaType.APPLICATION_JSON_VALUE)
class ClientController {
    private final ClientService clientService;

    @Autowired
    ClientController(ClientService clientsService) {
        this.clientService = clientsService;
    }

    @GetMapping
    public ResponseEntity<List<ClientView>> getClients() {
        final List<Client> clients = clientService.findAll();
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToViewList(clients, ClientView.class));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientView> getClient(@PathVariable UUID id) throws NotFoundException {
        final Client client = clientService.findById(id);
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToView(client, ClientView.class));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ClientView> addClient(@Valid @RequestBody ClientView clientView) {
        final Client createdClient = clientService.create(ModelMapper.convertToModel(clientView, Client.class));
        final URI baseLocation = ServletUriComponentsBuilder.fromCurrentRequest()
                .build().toUri();
        final String clientLocationString = String.format("/%s/%s", baseLocation, createdClient.getUuid());
        final URI location = UriComponentsBuilder.fromUriString(clientLocationString).build().toUri();
        return ResponseEntity
                .created(location)
                .body(ModelMapper.convertToView(createdClient, ClientView.class));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ClientView> updateClient(@PathVariable UUID id, @Valid @RequestBody ClientView clientView) {
        final Client updatedClient = clientService.update(id, ModelMapper.convertToModel(clientView, Client.class));
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToView(updatedClient, ClientView.class));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable UUID id) {
        clientService.delete(id);
        return ResponseEntity
                .ok()
                .build();
    }
}
