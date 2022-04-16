package com.hampolo.reminderapp.model;

import com.hampolo.reminderapp.model.enums.Role;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Users")
@Data
public class User extends Account{

  private String phoneNumber;

  private boolean remindByPhone;

  private boolean remindByEmail;

  private LocalTime remindedAtTime;

  private List<Reminder> reminders;

  @DBRef
  private List<Category> categories;


  public User() {
  }

  public User(String firstName, String lastName, String email, String password,
      String phoneNumber) {
    super(firstName, lastName, email, password, Role.User);
    this.reminders = new ArrayList<>();
    this.categories = new ArrayList<>();
    this.remindByEmail = true;
    this.remindByPhone = false;
    this.remindedAtTime = LocalTime.of(9, 0);
    this.phoneNumber = phoneNumber;
  }
}
