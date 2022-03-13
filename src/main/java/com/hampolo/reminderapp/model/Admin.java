package com.hampolo.reminderapp.model;


import com.hampolo.reminderapp.model.enums.Role;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Admins")
@Data
public class Admin extends Account {

  public Admin() {
  }

  public Admin(String firstName, String lastName, String email, String password) {
    super(firstName, lastName, email, password, Role.Admin);
  }
}
