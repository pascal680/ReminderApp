package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import com.hampolo.reminderapp.service.AccountService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  private final AccountService accountService;

  public AccountController(AccountService accountService) {
    this.accountService = accountService;
  }

  @GetMapping("/users/all")
  public List<User> getAllUsers(){
    return accountService.getAllUsers();
  }

//  @GetMapping("/admins/all")
//  public List<Admin> getAllAdmins(){
//    return adminRepository.findAll();
//  }


}
