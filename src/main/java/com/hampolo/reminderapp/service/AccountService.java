package com.hampolo.reminderapp.service;

import com.hampolo.reminderapp.model.Account;
import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.List;
import java.util.Optional;
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



  public Optional<Account> login(String email, String password){
    if(!userRepository.findByEmailIgnoreCase(email).equals(Optional.empty())){
      return Optional.of(userRepository.findByEmailAndPasswordIgnoreCase(email, password));
    }

    if(!adminRepository.findByEmailIgnoreCase(email).equals(Optional.empty())){
      return Optional.of(adminRepository.findByEmailAndPasswordIgnoreCase(email, password));
    }

    return Optional.empty();
  }

}
