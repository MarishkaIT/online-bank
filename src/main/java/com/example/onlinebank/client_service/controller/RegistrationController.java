package com.example.onlinebank.client_service.controller;

import com.example.onlinebank.client_service.entity.Client;
import com.example.onlinebank.client_service.exception.ClientExistsException;
import com.example.onlinebank.client_service.exception.InvalidClientDataException;
import com.example.onlinebank.client_service.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    ClientService clientService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }

    @PostMapping
    public String registerClient(@ModelAttribute("client") Client client, Model model) {
        try {
            clientService.createClient(client);
            return "redirect:/login";
        } catch (InvalidClientDataException | ClientExistsException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
