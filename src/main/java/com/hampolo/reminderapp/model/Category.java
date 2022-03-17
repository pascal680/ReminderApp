package com.hampolo.reminderapp.model;

import javax.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Categories")
@Data
public class Category {

  @Id
  private String id;

  private String title;
  private String description;

  public Category(){

  }

  public Category(String title, String description) {
    this.title = title;
    this.description = description;
  }
}
