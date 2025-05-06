package com.esther.VehicleService.services;

import com.esther.VehicleService.domain.UserEntity;

public interface UserService {

    UserEntity saveUser(UserEntity user);

    UserEntity findByEmail(String email);

    boolean checkPassword(String rawPassword, String hashedPassword);


}
