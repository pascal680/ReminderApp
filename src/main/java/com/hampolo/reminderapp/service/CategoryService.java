package com.hampolo.reminderapp.service;

import com.hampolo.reminderapp.dto.CategoryAddDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DuplicateDataException;
import com.hampolo.reminderapp.mapping.CategoryMapper;
import com.hampolo.reminderapp.mapping.ReminderMapper;
import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.CategoryRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  private final UserRepository userRepository;

  private final AccountService accountService;

  public CategoryService(CategoryRepository categoryRepository,
      UserRepository userRepository, AccountService accountService) {
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
    this.accountService = accountService;
  }

  public Category addUserCategory(CategoryAddDto categoryAddDto) throws AccountNotFoundException, DuplicateDataException {
    Optional<User> user = userRepository.findById(categoryAddDto.getUserId());

    if(user.isEmpty()){
      throw new AccountNotFoundException("Reminder user could not be found. Category: " + categoryAddDto);
    }

    List<Category> userCategories = user.map(User::getCategories).get();
    Category categoryEntity = CategoryMapper.toEntity(categoryAddDto);

    if(userCategories.stream()
        .anyMatch(category -> category.getTitle().equals(categoryEntity.getTitle()))){
      throw new DuplicateDataException("Category: " + categoryEntity+ " already exist for user: " + user);
    }

    Category returnedCategory = categoryRepository.save(categoryEntity);

    userCategories.add(returnedCategory);

    user.get().setCategories(userCategories);

    accountService.saveUser(user.get());

    return returnedCategory;
  }

  public List<Category> getUserCategories(String userId) throws AccountNotFoundException {
    return userRepository.findById(userId)
        .map(User::getCategories)
        .orElseThrow(()->new AccountNotFoundException("Account was not found")).stream().sorted(
            Comparator.comparing(Category::getTitle)).collect(Collectors.toList());
  }


}
