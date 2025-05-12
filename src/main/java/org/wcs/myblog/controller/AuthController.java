package org.wcs.myblog.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.wcs.myblog.dto.ProfileDTO;
import org.wcs.myblog.dto.UserLoginDTO;
import org.wcs.myblog.dto.UserRegistrationDTO;
import org.wcs.myblog.model.User;
import org.wcs.myblog.security.AuthenticationService;
import org.wcs.myblog.service.UserService;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    public AuthController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO userRegistrationDTO) {
        User registeredUser = userService.registerUser(
                userRegistrationDTO.getEmail(),
                userRegistrationDTO.getPassword(),
                Set.of("ROLE_USER") // Par défaut, chaque utilisateur aura le rôle "USER"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody UserLoginDTO userLoginDTO) {
        String token = authenticationService.authenticate(
                userLoginDTO.getEmail(),
                userLoginDTO.getPassword()
        );
        return ResponseEntity.ok(token);
    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDTO> getProfile(Principal principal) {
        String email = principal.getName();
        ProfileDTO profile = new ProfileDTO();
        profile.setEmail(email);
        return ResponseEntity.ok(profile);
    }
}
