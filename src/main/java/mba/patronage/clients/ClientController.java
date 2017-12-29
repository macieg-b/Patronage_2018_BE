package mba.patronage.clients;

import mba.patronage.clients.model.db.Client;
import mba.patronage.clients.model.view.ClientView;
import mba.patronage.config.Url;
import mba.patronage.util.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = Url.URL_CLIENT, produces = MediaType.APPLICATION_JSON_VALUE)
class ClientController {
    private final ClientService clientsService;

    @Autowired
    ClientController(ClientService clientsService) {
        this.clientsService = clientsService;
    }

    @GetMapping
    public ResponseEntity<List<ClientView>> getAll(){
        final List<Client> clients = clientsService.findAll();
        return ResponseEntity
                .ok()
                .body(ModelMapper.convertToViewList(clients, ClientView.class));
    }
}
