package com.mareknowakowski.meds.controllers;

import com.mareknowakowski.meds.domain.User;
import com.mareknowakowski.meds.dtos.UserDTO;
import com.mareknowakowski.meds.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {
    private final UserService userService;

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/user", consumes = "application/json", produces = "application/json")
    public ResponseEntity createUserIfNotExists(@RequestBody UserDTO userDTO){
        User user = userDTO.convertSelfToUser();
        user = userService.createOrFindUser(user.getDeviceId());
        return new ResponseEntity<>(user.getId(), HttpStatus.OK);
    }
}
