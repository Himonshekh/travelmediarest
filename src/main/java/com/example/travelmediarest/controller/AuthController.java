package com.example.travelmediarest.controller;

import com.example.travelmediarest.dto.RegistrationDto;
import com.example.travelmediarest.dto.UserDto;
import com.example.travelmediarest.model.User;
import com.example.travelmediarest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registrationHandler(@Valid @RequestBody RegistrationDto registrationDto, Errors errors, BeanPropertyBindingResult res) {
        Map<String, Object> objectMap = new HashMap<>();
//        log.info("bean errorss::::  "+res.getAllErrors().get(1).getDefaultMessage());
        if (errors.hasErrors()) {
//            log.info("See errors: " + errors.getAllErrors().size());
//            objectMap.put("error", errors);
            return new ResponseEntity<>(objectMap, HttpStatus.BAD_REQUEST);
        }
        log.info("where????");
        User user = userService.isAlreadyOrSaveUSer(registrationDto);
        if (user == null) {
            objectMap.put("error", new String[]{"mail already in use"});
            return new ResponseEntity<>(objectMap, HttpStatus.ALREADY_REPORTED);
        } else {
            objectMap.put("account", user);
        }
        return new ResponseEntity<>(objectMap, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginHandler(@RequestBody UserDto userDto) {
        Map<String, Object> objectMap = new HashMap<>();
        try {
            objectMap = userService.authenticateByCredentials(userDto);
            return new ResponseEntity<>(objectMap, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            objectMap.put("error", new RuntimeException("invalid login Credentials"));
            return new ResponseEntity<>(objectMap, HttpStatus.NOT_FOUND);
        }
    }
}
