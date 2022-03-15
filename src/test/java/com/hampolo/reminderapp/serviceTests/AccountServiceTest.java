package com.hampolo.reminderapp.serviceTests;

import com.hampolo.reminderapp.dto.LoginRequestDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.WrongCredentialsException;
import com.hampolo.reminderapp.model.Account;
import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.repository.AdminRepository;
import com.hampolo.reminderapp.repository.UserRepository;
import com.hampolo.reminderapp.service.AccountService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
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
  public void testGetAllUsers(){
    List<User> expected = getUsers();
    when(userRepository.findAll()).thenReturn(expected);

    List<User> returned = accountService.getAllUsers();

    assertThat(returned).hasSize(expected.size());
  }

  @Test
  public void testLoginUserSuccess() throws WrongCredentialsException, AccountNotFoundException {

    User expected = getUsers().get(0);
    LoginRequestDto request = new LoginRequestDto(expected.getEmail(),expected.getPassword());
    when(userRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.of(expected));
    when(userRepository.findByEmailIgnoreCaseAndPassword(any(String.class),any(String.class))).thenReturn(Optional.of(expected));

    Account returned = accountService.login(request);

    assertThat(returned).isEqualTo(expected);
  }

  @Test
  public void testLoginAdminSuccess() throws WrongCredentialsException, AccountNotFoundException {
    Admin expected = getAdmins().get(0);
    LoginRequestDto request = new LoginRequestDto(expected.getEmail(),expected.getPassword());
    when(adminRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.of(expected));
    when(adminRepository.findByEmailIgnoreCaseAndPassword(any(String.class),any(String.class))).thenReturn(Optional.of(expected));

    Account returned = accountService.login(request);

    assertThat(returned).isEqualTo(expected);
  }

  @Test
  public void testLoginUserUnknownError() {

    User expected = getUsers().get(0);
    LoginRequestDto request = new LoginRequestDto(expected.getEmail(),expected.getPassword());
    when(userRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.empty());


    assertThatThrownBy(()->accountService.login(request)).isInstanceOf(AccountNotFoundException.class);
  }

  @Test
  public void testLoginUserWrongCredentialsError(){

    User expected = getUsers().get(0);
    LoginRequestDto request = new LoginRequestDto(expected.getEmail(),expected.getPassword());
    when(userRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.of(expected));
    when(userRepository.findByEmailIgnoreCaseAndPassword(any(String.class),any(String.class))).thenReturn(Optional.empty());

    assertThatThrownBy(()->accountService.login(request)).isInstanceOf(WrongCredentialsException.class);
  }

  @Test
  public void testLoginAdminUnknownError(){
    Admin expected = getAdmins().get(0);
    LoginRequestDto request = new LoginRequestDto(expected.getEmail(),expected.getPassword());
    when(adminRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.empty());

    assertThatThrownBy(()->accountService.login(request)).isInstanceOf(AccountNotFoundException.class);
  }

  @Test
  public void testLoginAdminWrongCredentialsError(){
    Admin expected = getAdmins().get(0);
    LoginRequestDto request = new LoginRequestDto(expected.getEmail(),expected.getPassword());
    when(adminRepository.findByEmailIgnoreCase(any(String.class))).thenReturn(Optional.of(expected));
    when(adminRepository.findByEmailIgnoreCaseAndPassword(any(String.class),any(String.class))).thenReturn(Optional.empty());

    assertThatThrownBy(()->accountService.login(request)).isInstanceOf(WrongCredentialsException.class);
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
