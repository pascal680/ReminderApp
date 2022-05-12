package com.hampolo.reminderapp.mapping;

import com.hampolo.reminderapp.dto.UserRegistrationFormDto;
import com.hampolo.reminderapp.model.User;

public abstract class UserMapper {

  public static User toEntity(UserRegistrationFormDto userDto){
    return new User(userDto.getFirstName(),
        userDto.getLastName(),
        userDto.getEmail(),
        userDto.getPassword(),
        userDto.getPhoneNumber(),
        userDto.isRemindByEmail(),
        userDto.isRemindByPhone(),
        userDto.getRemindedAtTIme());
  }

}
