package com.hampolo.reminderapp.controllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hampolo.reminderapp.controller.AccountController;
import com.hampolo.reminderapp.dto.CategoryAddDto;
import com.hampolo.reminderapp.dto.LoginRequestDto;
import com.hampolo.reminderapp.dto.UserRegistrationFormDto;
import com.hampolo.reminderapp.exceptions.AccountNotFoundException;
import com.hampolo.reminderapp.exceptions.DuplicateDataException;
import com.hampolo.reminderapp.exceptions.DuplicateUserException;
import com.hampolo.reminderapp.exceptions.WrongCredentialsException;
import com.hampolo.reminderapp.model.Account;
import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.Category;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.model.enums.Role;
import com.hampolo.reminderapp.repository.UserRepository;
import com.hampolo.reminderapp.service.AccountService;
import com.hampolo.reminderapp.service.CategoryService;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ContextConfiguration(
    classes = AccountController.class,
    initializers = ConfigFileApplicationContextInitializer.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

  private final ObjectMapper mapper;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AccountService accountService;

  @MockBean
  private CategoryService categoryService;

  public AccountControllerTest() {
    this.mapper = new ObjectMapper().findAndRegisterModules();
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void testGetAllUsers() throws Exception {
    List<User> expected = getUsers();
    when(accountService.getAllUsers()).thenReturn(expected);

    MvcResult result =
        mockMvc.perform(
                get("/account/users/all")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(mapper.writeValueAsString(expected)))
            .andReturn();

    var actualUsers = mapper.readValue(result.getResponse().getContentAsString(), List.class);
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(actualUsers.size()).isEqualTo(expected.size());
  }

  @Test
  public void testLoginSuccess()
      throws Exception {
    LoginRequestDto loginRequestDto = new LoginRequestDto("test@test.com", "password");
    //Account expected = getUsers().get(0); why does this return a different object
    /* Expected :User(phoneNumber=12345, categories=[])
    Actual   :Account(id=null, firstName=User1, lastName=UserLN1, email=user@test, password=password, dateCreated=2022-03-15T00:48:46.105278500, role=User)
     */
    Account expected = new Account("Test", "TestLN", "test@test.com", "password", Role.User);

    when(accountService.login(loginRequestDto)).thenReturn(expected);

    MvcResult result =
        mockMvc.perform(
            post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequestDto)))
            .andReturn();

    var actualUser = mapper.readValue(result.getResponse().getContentAsString(), Account.class);
    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
    assertThat(actualUser).isEqualTo(expected);

  }

  @Test
  public void testLoginUnknownError()
      throws Exception {
    LoginRequestDto loginRequestDto = new LoginRequestDto("test@test.com", "password");
    //Account expected = getUsers().get(0); why does this return a different object
    /* Expected :User(phoneNumber=12345, categories=[])
    Actual   :Account(id=null, firstName=User1, lastName=UserLN1, email=user@test, password=password, dateCreated=2022-03-15T00:48:46.105278500, role=User)
     */
    Account expected = new Account("Test", "TestLN", "test@test.com", "password", Role.User);

    when(accountService.login(loginRequestDto)).thenThrow(AccountNotFoundException.class);

    MvcResult result =
        mockMvc.perform(
            post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequestDto)))
            .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());

  }

  @Test
  public void testLoginWrongCredentialsError()
      throws Exception {
    LoginRequestDto loginRequestDto = new LoginRequestDto("test@test.com", "password");
    //Account expected = getUsers().get(0); why does this return a different object
    /* Expected :User(phoneNumber=12345, categories=[])
    Actual   :Account(id=null, firstName=User1, lastName=UserLN1, email=user@test, password=password, dateCreated=2022-03-15T00:48:46.105278500, role=User)
     */
    Account expected = new Account("Test", "TestLN", "test@test.com", "password", Role.User);

    when(accountService.login(loginRequestDto)).thenThrow(WrongCredentialsException.class);

    MvcResult result =
        mockMvc.perform(
            post("/account/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(loginRequestDto)))
            .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());

  }

  @Test
  public void testRegisterUserSuccess()  throws Exception {
    UserRegistrationFormDto userRegisterRequest = new UserRegistrationFormDto("Test", "TestLN", "test@test", "password", "123456789");
    User expected = getUsers().get(0);

    when(accountService.registerUser(userRegisterRequest)).thenReturn(expected);

    MvcResult result = mockMvc.perform(post("/account/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRegisterRequest)))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }


  @Test
  public void testRegisterUserError()  throws Exception {
    UserRegistrationFormDto userRegisterRequest = new UserRegistrationFormDto("Test", "TestLN", "test@test", "password", "123456789");

    when(accountService.registerUser(userRegisterRequest)).thenThrow(DuplicateUserException.class);

    MvcResult result = mockMvc.perform(post("/account/user/register")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(userRegisterRequest)))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
  }

  @Test
  public void testSaveCategorySuccess() throws Exception{
    CategoryAddDto categoryAddRequest = new CategoryAddDto("123", "test");
    Category expected = getCategories().get(0);

    when(categoryService.addUserCategory(categoryAddRequest)).thenReturn(expected);

    MvcResult result = mockMvc.perform(post("/account/user/category/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(categoryAddRequest)))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testDuplicateCategoryError() throws Exception{
    CategoryAddDto categoryAddRequest = new CategoryAddDto("123", "test");
    Category expected = getCategories().get(0);

    when(categoryService.addUserCategory(categoryAddRequest)).thenThrow(DuplicateDataException.class);

    MvcResult result = mockMvc.perform(post("/account/user/category/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(categoryAddRequest)))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.CONFLICT.value());
  }

  @Test
  public void testSaveCategoryAccountNotFoundError() throws Exception{
    CategoryAddDto categoryAddRequest = new CategoryAddDto("123", "test");
    Category expected = getCategories().get(0);

    when(categoryService.addUserCategory(categoryAddRequest)).thenThrow(AccountNotFoundException.class);

    MvcResult result = mockMvc.perform(post("/account/user/category/save")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(categoryAddRequest)))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
  }

  @Test
  public void testGetUserCategoriesSuccess() throws Exception{
    List<Category> expected = getCategories();

    when(categoryService.getUserCategories("1")).thenReturn(expected);

    MvcResult result = mockMvc.perform(get("/account/user/1/categories")
        .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());
  }

  @Test
  public void testGetUserCategoriesAccountNotFoundError() throws Exception{
    List<Category> expected = getCategories();

    when(categoryService.getUserCategories("1")).thenThrow(AccountNotFoundException.class);

    MvcResult result = mockMvc.perform(get("/account/user/1/categories")
        .contentType(MediaType.APPLICATION_JSON))
        .andReturn();

    assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
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

  private List<Category> getCategories(){
    return List.of(
        new Category("test"),
        new Category("test2")
    );
  }
}
