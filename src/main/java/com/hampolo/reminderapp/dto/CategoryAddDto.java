package com.hampolo.reminderapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddDto {

  private String userId;

  private String title;

  private String description;

}
