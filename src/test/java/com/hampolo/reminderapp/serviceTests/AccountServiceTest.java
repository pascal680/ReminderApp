package com.hampolo.reminderapp.serviceTests;

import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import com.hampolo.reminderapp.service.AccountService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

  @Mock
  private UserRepository userRepository;

  @Mock
  private AdminRepository adminRepository;

  @InjectMocks
  private AccountService accountService;


  @Test
  public void getAllUsers(){
    List<User> expected = getUsers();
    when(userRepository.findAll()).thenReturn(expected);

    List<User> returned = accountService.getAllUsers();

    assertThat(returned).hasSize(expected.size());
  }

  private List<User> getUsers() {
    return List.of(
        new User("User1", "UserLN1", "user@test", "password", "12345"),
        new User("User2", "UserLN2", "user2@test", "password2", "123456")
    );
  }
  private List<Admin> getAdmins(){
    return List.of(
        new Admin("Admin1", "AdminLN1", "admin@test", "password"),
        new Admin("Admin2", "AdminLN2", "admin2@test", "password2")
    );
  }

}
