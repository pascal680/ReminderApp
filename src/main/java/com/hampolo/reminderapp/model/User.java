package com.hampolo.reminderapp.model;

import javax.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class User extends Account{

  private String phoneNumber;


}
