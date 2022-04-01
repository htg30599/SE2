package se2Project.service;

import se2Project.controller.dto.UserRegistrationDto;
import se2Project.model.User;

public interface UserService {
    User save(UserRegistrationDto registrationDto);

}
