package com.hampolo.reminderapp.controllerTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hampolo.reminderapp.controller.AccountController;
import com.hampolo.reminderapp.model.Admin;
import com.hampolo.reminderapp.model.User;
import com.hampolo.reminderapp.service.AccountService;
import java.util.List;
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

  public AccountControllerTest() {
    this.mapper = new ObjectMapper().findAndRegisterModules();
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void getAllUsers() throws Exception {
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
