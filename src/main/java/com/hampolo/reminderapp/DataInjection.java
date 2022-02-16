package com.hampolo.reminderapp;

import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.CategoryRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInjection implements CommandLineRunner {
  private UserRepository userRepository;
  private CategoryRepository categoryRepository;

  public DataInjection(UserRepository userRepository,
      CategoryRepository categoryRepository) {
    this.userRepository = userRepository;
    this.categoryRepository = categoryRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Category categoryPayment = new Category("Payment", "Client Payment");
    Category categoryTest = new Category("Test", "Category Test");

    categoryRepository.deleteAll();
    categoryRepository.saveAll(Arrays.asList(categoryPayment, categoryTest));

    User user = new User("Pascal",
        "Bourgoin",
        "pascalbourgoindev@gmail.com",
        "password",
        "5149100627");



    user.setCategories(Arrays.asList(categoryPayment, categoryTest));

    User user2 = new User("Pascal1",
        "Bourgoin1",
        "pascalbourgoindev@gmail.com1",
        "password",
        "51491006271");

    user2.setCategories(Arrays.asList(categoryPayment, categoryTest));

    userRepository.deleteAll();
    userRepository.saveAll(Arrays.asList(user, user2));
  }
}