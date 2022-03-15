package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.dto.LoginRequestDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.WrongCredentialsException;
import com.hampolo.reminderapp.model.Account;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.service.AccountService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

  @PostMapping("/login")
  public ResponseEntity<Account> login(@RequestBody LoginRequestDto loginRequestDto)
      throws WrongCredentialsException, AccountNotFoundException {
    return new ResponseEntity<>(accountService.login(loginRequestDto), HttpStatus.OK);
  }


}
