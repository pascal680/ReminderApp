package com.hampolo.reminderapp.service;

import com.hampolo.reminderapp.dto.LoginRequestDto;
import com.hampolo.reminderapp.dto.UserRegistrationFormDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DuplicateUserException;
import com.hampolo.reminderapp.exceptions.WrongCredentialsException;
import com.hampolo.reminderapp.mapping.UserMapper;
import com.hampolo.reminderapp.model.Account;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

  private final UserRepository userRepository;
  private final AdminRepository adminRepository;

  public AccountService(UserRepository userRepository,
      AdminRepository adminRepository) {
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
  }

  public List<User> getAllUsers(){
    return userRepository.findAll();
  }



  public Account login(LoginRequestDto loginRequestDto)
      throws WrongCredentialsException, AccountNotFoundException {
    if(userRepository.findByEmailIgnoreCase(loginRequestDto.getEmail()).isPresent()){
      return userRepository.findByEmailIgnoreCaseAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
          .orElseThrow(()->new WrongCredentialsException("Wrong credentials!"));
    }

    else if(adminRepository.findByEmailIgnoreCase(loginRequestDto.getEmail()).isPresent()){
      return adminRepository.findByEmailIgnoreCaseAndPassword(loginRequestDto.getEmail(), loginRequestDto.getPassword())
          .orElseThrow(()->new WrongCredentialsException("Wrong credentials!"));
    }

    throw new AccountNotFoundException("User was not found!");
  }

  public User registerUser(UserRegistrationFormDto userDto) throws DuplicateUserException {
    if(userRepository.findByEmailIgnoreCase(userDto.getEmail()).isPresent()){
      throw new DuplicateUserException("User already exist");
    }

    return userRepository.save(UserMapper.toEntity(userDto));
  }

  public User saveUser(User user) throws AccountNotFoundException {
    if(userRepository.findById(user.getId()).isEmpty()){
      throw new AccountNotFoundException("User could not be foud to perform the save. User: " + user);
    }
    return userRepository.save(user);
  }

}
