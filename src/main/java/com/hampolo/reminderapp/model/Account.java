package com.hampolo.reminderapp.model;

import java.time.LocalDate;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;

@MappedSuperclass
@Data
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  protected int id;
  protected String firstName;
  protected String lastName;
  protected String email;
  protected String password;
  protected LocalDate dateCreated;

  public Account(){
    this.dateCreated = LocalDate.now();
  }

  public Account(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.dateCreated = LocalDate.now();
  }
}
