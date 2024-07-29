package com.example.onlinebank.client_service.controller;

import com.example.onlinebank.client_service.entity.Client;
import com.example.onlinebank.client_service.exception.ClientExistsException;
import com.example.onlinebank.client_service.exception.InvalidClientDataException;
import com.example.onlinebank.client_service.service.ClientService;
import com.example.onlinebank.notification_service.service.EmailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;

@RestController
@RequestMapping("/api/client")
public class ClientController {

    private ClientService clientService;

    private EmailService emailService;

    @GetMapping
    public List<Client> getAllClients() {
        return clientService.getAllClients();
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        return clientService.getClientById(clientId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> notFound().build());
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            Client newClient = clientService.createClient(client);
            emailService.sendEmail(newClient.getId(), "Welcome!", "You have successfully registered in our system!");
            return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
        }catch (InvalidClientDataException e) {
            return ResponseEntity.badRequest().build();
        }catch (ClientExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PutMapping
    public ResponseEntity<Client> updateClient(@RequestBody Client client) {
        Client updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long clientId) {
        clientService.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }
}
