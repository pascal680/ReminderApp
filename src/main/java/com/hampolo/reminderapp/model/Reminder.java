package com.hampolo.reminderapp.model;

import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Reminder {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  private String reminderTitle;
  private LocalDate reminderDate;
  private LocalDate reminderCreationDate;


}
