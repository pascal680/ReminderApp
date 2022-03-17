package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.dto.CategoryAddDto;
import com.hampolo.reminderapp.dto.LoginRequestDto;
import com.hampolo.reminderapp.dto.UserRegistrationFormDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DuplicateDataException;
import com.hampolo.reminderapp.exceptions.DuplicateUserException;
import com.hampolo.reminderapp.exceptions.WrongCredentialsException;
import com.hampolo.reminderapp.model.Account;
import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.service.AccountService;
import com.hampolo.reminderapp.service.CategoryService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

  private final AccountService accountService;

  private final CategoryService categoryService;

  public AccountController(AccountService accountService,
      CategoryService categoryService) {
    this.accountService = accountService;
    this.categoryService = categoryService;
  }

  @GetMapping("/users/all")
  public ResponseEntity<List<User>> getAllUsers(){
    return new ResponseEntity<>(accountService.getAllUsers(),HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<Account> login(@RequestBody LoginRequestDto loginRequestDto)
      throws WrongCredentialsException, AccountNotFoundException {
    return new ResponseEntity<>(accountService.login(loginRequestDto), HttpStatus.OK);
  }

  @PostMapping("/user/register")
  public ResponseEntity<User> registerUser(@RequestBody UserRegistrationFormDto userRegistrationFormDto)
      throws DuplicateUserException {
    return new ResponseEntity<>(accountService.registerUser(userRegistrationFormDto), HttpStatus.OK);
  }

  @PostMapping("/user/category/save")
  public ResponseEntity<Category> saveCategory(@RequestBody CategoryAddDto categoryAddDto)
      throws DuplicateDataException, AccountNotFoundException {
    return new ResponseEntity<>(categoryService.addUserCategory(categoryAddDto), HttpStatus.OK);
  }

  @GetMapping("/user/{userId}/categories")
  public ResponseEntity<List<Category>> getUserCategories(@PathVariable String userId)
      throws AccountNotFoundException {
    return new ResponseEntity<>(categoryService.getUserCategories(userId), HttpStatus.OK);
  }


}
