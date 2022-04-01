package se2Project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se2Project.controller.dto.UserRegistrationDto;
import se2Project.model.Role;
import se2Project.model.User;
import se2Project.repository.UserRepository;

import java.util.Arrays;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getUserName(),
                registrationDto.getPhoneNumber(),
                registrationDto.getEmail(),
                registrationDto.getPassword(),
                registrationDto.getAddress(),
                registrationDto.getGender(), Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }
}
