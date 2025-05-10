package com.esther.VehicleService.controllers;

import com.esther.VehicleService.domain.LoginRequest;
import com.esther.VehicleService.domain.SignupRequest;
import com.esther.VehicleService.domain.UserEntity;
import com.esther.VehicleService.security.JwtUtil;
import com.esther.VehicleService.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@CrossOrigin(origins = "${CORS_ORIGIN}")
@RestController("/auth")
public class AuthController {

    private final AuthService authService;

    private final JwtUtil jwtUtil;

    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequest signupRequest) {

        String email = signupRequest.getEmail();
        String username = signupRequest.getUsername();
        if (authService.existsByEmail(email)) {
            return new ResponseEntity<>("Email is already in use!", HttpStatus.BAD_REQUEST);
        }
        if (authService.existsByUsername(username)){
            return new ResponseEntity<>("Username is already in use!", HttpStatus.BAD_REQUEST);
        }
        authService.createUser(signupRequest);
        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest){

        String password = loginRequest.getPassword();
        UserEntity user = authService.findByEmail(loginRequest.getEmail());

        if (user == null || !authService.checkPassword(password, user.getPassword())) {
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(user.getUsername());
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}
