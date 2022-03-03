package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  private UserRepository userRepository;
  private AdminRepository adminRepository;

  public AccountController(UserRepository userRepository,
      AdminRepository adminRepository) {
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
  }

  @GetMapping("/users/all")
  public List<User> getAllUsers(){
    return userRepository.findAll();
  }

  @GetMapping("/admins/all")
  public List<Admin> getAllAdmins(){
    return adminRepository.findAll();
  }


}
