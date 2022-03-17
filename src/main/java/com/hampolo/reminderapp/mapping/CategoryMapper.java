package com.hampolo.reminderapp.mapping;

import com.hampolo.reminderapp.dto.CategoryAddDto;
import com.hampolo.reminderapp.model.Category;

public abstract class CategoryMapper {

  public static Category toEntity(CategoryAddDto categoryAddDto){
    return new Category(categoryAddDto.getTitle(),
        categoryAddDto.getDescription());
  }

}
