package com.example.onlinebank.client_service.service;

import com.example.onlinebank.client_service.entity.Client;
import com.example.onlinebank.client_service.exception.ClientExistsException;
import com.example.onlinebank.client_service.exception.InvalidClientDataException;
import com.example.onlinebank.client_service.repository.ClientRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;
    private MessageSource messageSource;



    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    @Transactional
    public Client createClient(Client client) throws InvalidClientDataException, ClientExistsException {

        if (client.getFirstName() == null || client.getLastName() == null || client.getEmail() == null) {
            throw new InvalidClientDataException(messageSource.getMessage("error.invalid.client.data", null, LocaleContextHolder.getLocale()));
        }

        if (clientRepository.findByEmail(client.getEmail()).isPresent()) {
            throw new ClientExistsException(messageSource.getMessage("error.client.exists", null, LocaleContextHolder.getLocale()));
        }

        return clientRepository.save(client);
    }

    @Transactional
    public Client updateClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClientById(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
