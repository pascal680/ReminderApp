package com.hampolo.reminderapp.model;

import java.io.Serializable;
import javax.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class Admin extends Account {

  private String role;

  public Admin() {
  }

}
