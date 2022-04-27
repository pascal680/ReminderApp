package com.hampolo.reminderapp.serviceTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.CategoryRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import com.hampolo.reminderapp.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

  @Mock
  private CategoryRepository categoryRepository;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Test
  public void testGetUserCategories() throws Exception{
    List<Category> expected = getCategories();
    User user = new User();
    user.setCategories(expected);

    when(userRepository.findById(any(String.class))).thenReturn(Optional.of(user));

    assertThat(categoryService.getUserCategories("userId")).isEqualTo(expected);
  }


  private List<Category> getCategories() {
    return List.of(
        new Category("Category 1"),
        new Category("Category 2"),
        new Category("Category 3")
        );
  }


}
