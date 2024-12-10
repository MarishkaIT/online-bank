package com.example.onlinebank.security_service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }
    @PostMapping("/login-processing")
    public String processLogin(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model, HttpServletRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return "redirect:/dashboard";
        } catch (BadCredentialsException e) {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }

}
