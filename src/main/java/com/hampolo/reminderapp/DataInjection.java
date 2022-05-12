package com.hampolo.reminderapp;

import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.Reminder;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.CategoryRepository;
import com.hampolo.reminderapp.repository.ReminderRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInjection implements CommandLineRunner {
  private UserRepository userRepository;
  private AdminRepository adminRepository;
  private CategoryRepository categoryRepository;
  private ReminderRepository reminderRepository;

  public DataInjection(UserRepository userRepository,
      AdminRepository adminRepository,
      CategoryRepository categoryRepository,
      ReminderRepository reminderRepository) {
    this.userRepository = userRepository;
    this.adminRepository = adminRepository;
    this.categoryRepository = categoryRepository;
    this.reminderRepository = reminderRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Category categoryPayment = new Category("Payment");
    Category categoryTest = new Category("Test");

    categoryRepository.deleteAll();
    categoryRepository.saveAll(Arrays.asList(categoryPayment, categoryTest));

    Reminder reminder1 = new Reminder("Test reminder1", LocalDateTime.now(), false);
    Reminder reminder2 = new Reminder("Test reminder2", LocalDateTime.now(), false);

    reminderRepository.deleteAll();
    reminderRepository.saveAll(List.of(reminder1, reminder2));

    User user = new User("Pascal",
        "Bourgoin",
        "pascalbourgoindev@gmail.com",
        "password",
        "5149100627",
        false,
        true,
        LocalTime.now());

    user.setReminders(List.of(reminder1));

    user.setCategories(Arrays.asList(categoryPayment, categoryTest));

    User user2 = new User("Pascal1",
        "Bourgoin1",
        "pascalbourgoindev@gmail.com1",
        "password",
        "51491006271",
        false,
        true,
        LocalTime.now());

    user2.setReminders(List.of(reminder1,reminder2));

    user2.setCategories(Arrays.asList(categoryPayment, categoryTest));

    userRepository.deleteAll();
    userRepository.saveAll(Arrays.asList(user, user2));

    Admin admin = new Admin("Admin", "AdminLN", "admin@email.com", "password" );

    adminRepository.deleteAll();
    adminRepository.save(admin);

    Optional<User> byEmail = userRepository.findByEmailIgnoreCase("pascalbourgoindev@gmail.com");
    Optional<User> byEmailAndPassword = userRepository
        .findByEmailIgnoreCaseAndPassword("pascalbourgoindev@gmail.com", "password");

    System.err.println(byEmail);
    System.err.println(byEmailAndPassword);
  }
}
