package it.szyszka.user.controller;

import it.szyszka.user.model.User;
import it.szyszka.user.service.UserService;
import it.szyszka.user.service.UserServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by rafal on 27.12.17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(value = UserApiController.class, secure = false)
@WebAppConfiguration
public class UserApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    User mockUser = new User("Test", "Test User", "test@email.com", "test");

    String mockAuthHeader = "Basic " + Base64Utils.encodeToString("testUser:test".getBytes());

    @Test
    public void userExists_signIn_shouldReturnUserData() throws Exception {

        Mockito
                .when(userService.findUserByEmail(Mockito.anyString()))
                .thenReturn(mockUser);

        mockMvc.perform(
                get("api/user/login")
                        .header(HttpHeaders.AUTHORIZATION, mockAuthHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", is("Test")))
                .andExpect(jsonPath("$.fullName", is("Test User")))
                .andExpect(jsonPath("$.email", is("test@email.com")));

        Mockito.verify(userService, Mockito.times(1)).findUserByEmail(Mockito.anyString());

    }

    @Test
    public void userNotExists_signIn_shouldReturnNull() throws Exception {

        Mockito
                .when(userService.findUserByEmail(Mockito.anyString()))
                .thenReturn(mockUser);

        mockMvc.perform(
                get("api/user/login")
                        .header(HttpHeaders.AUTHORIZATION, mockAuthHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nickname", isEmptyOrNullString()))
                .andExpect(jsonPath("$.fullName", isEmptyOrNullString()))
                .andExpect(jsonPath("$.email", isEmptyOrNullString()));

        Mockito.verify(userService, Mockito.times(1)).findUserByEmail(Mockito.anyString());

    }

}
