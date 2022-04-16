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

  public Category(){

  }

  public Category(String title) {
    this.title = title;
  }
}
