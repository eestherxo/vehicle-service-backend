package com.esther.VehicleService.controllers;

import com.esther.VehicleService.domain.UserDto;
import com.esther.VehicleService.domain.UserEntity;
import com.esther.VehicleService.mappers.implement.UserMapper;
import com.esther.VehicleService.security.JwtUtil;
import com.esther.VehicleService.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    private final UserMapper mapper;

    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> sign(@RequestBody UserDto userDto) {

        String email = userDto.getEmail();

        if (userService.findByEmail(email) == null) {
            UserEntity userEntity = mapper.mapFrom(userDto);
            userService.saveUser(userEntity);
            return new ResponseEntity<>(userDto, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserDto userDto){

        String email = userDto.getEmail();
        String password = userDto.getPassword();
        UserEntity foundUser = userService.findByEmail(email);

        if (foundUser == null || !userService.checkPassword(password, foundUser.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String token = jwtUtil.generateToken(email);
        return null;
    }
}
