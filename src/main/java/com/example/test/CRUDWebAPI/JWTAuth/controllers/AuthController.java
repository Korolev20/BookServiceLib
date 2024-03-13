package com.example.test.CRUDWebAPI.JWTAuth.controllers;

import com.example.test.CRUDWebAPI.JWTAuth.dto.AuthDTO;
import com.example.test.CRUDWebAPI.JWTAuth.dto.HumanDTO;
import com.example.test.CRUDWebAPI.JWTAuth.models.Human;
import com.example.test.CRUDWebAPI.JWTAuth.security.JWTCreate;
import com.example.test.CRUDWebAPI.JWTAuth.services.RegistrationService;
import com.example.test.CRUDWebAPI.JWTAuth.utils.HumanValidator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
@RequestMapping("/auth")
@Tag(name = "AuthController", description = "Controller for working with authentication")

public class AuthController {

    private final RegistrationService registrationService;
    private final HumanValidator personValidator;
    private final JWTCreate jwtCreate;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(RegistrationService registrationService, HumanValidator personValidator, JWTCreate jwtCreate, ModelMapper modelMapper, AuthenticationManager authenticationManager) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtCreate = jwtCreate;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
    }


    @PostMapping("/registration")
    @Operation(summary = "registration with token")
    public Map<String, String> performRegistration(@RequestBody @Valid HumanDTO personDTO,
                                                   BindingResult bindingResult) {
        Human human = convertToPerson(personDTO);

        personValidator.validate(human, bindingResult);

        if (bindingResult.hasErrors()) {
            return Map.of("message", "There is already a human with the same name");
        }

        registrationService.register(human);

        String token = jwtCreate.generateToken(human.getUsername());
        return Map.of("jwt-token", token);
    }


    @PostMapping("/login")
    @Operation(summary = "login with token")
    public Map<String, String> performLogin(@RequestBody AuthDTO authenticationDTO) {
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(authenticationDTO.getUsername(),
                        authenticationDTO.getPassword());

        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect data");
        }

        String token = jwtCreate.generateToken(authenticationDTO.getUsername());
        return Map.of("jwt-token", token);
    }
    public Human convertToPerson(HumanDTO personDTO) {
        return this.modelMapper.map(personDTO,Human.class);
    }
}
