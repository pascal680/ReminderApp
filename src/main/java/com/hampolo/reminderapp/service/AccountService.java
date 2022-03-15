package com.hampolo.reminderapp.service;

import com.hampolo.reminderapp.dto.LoginRequestDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.WrongCredentialsException;
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

}
