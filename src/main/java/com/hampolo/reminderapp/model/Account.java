package com.hampolo.reminderapp.model;

import com.hampolo.reminderapp.model.enums.Role;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Id;
import lombok.Data;

@Data
public class Account {

  @Id
  protected String id;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected String password;
  protected LocalDateTime dateCreated;
  protected Role role;

  public Account(){
    this.dateCreated = LocalDateTime.now();
  }

  public Account(String firstName, String lastName, String email, String password, Role role) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.dateCreated = LocalDateTime.now();
  }
}
