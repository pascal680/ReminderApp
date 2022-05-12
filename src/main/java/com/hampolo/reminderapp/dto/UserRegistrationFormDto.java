package com.hampolo.reminderapp.dto;

import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationFormDto {

  private String firstName;

  private String lastName;

  private String email;

  private String password;

  private String phoneNumber;

  private boolean remindByPhone;

  private boolean remindByEmail;

  private LocalTime remindedAtTIme;

}
