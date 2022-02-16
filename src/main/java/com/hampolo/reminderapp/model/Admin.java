package com.hampolo.reminderapp.model;


import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Admins")
@Data
public class Admin extends Account {

  private String role;

  public Admin() {
  }

  public Admin(String firstName, String lastName, String email, String password,
      String role) {
    super(firstName, lastName, email, password);
    this.role = role;
  }
}
