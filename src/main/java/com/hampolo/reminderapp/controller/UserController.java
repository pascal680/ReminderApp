package com.hampolo.reminderapp.controller;

import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

  private UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/all")
  public List<User> getAll(){
    return userRepository.findAll();
  }

}
