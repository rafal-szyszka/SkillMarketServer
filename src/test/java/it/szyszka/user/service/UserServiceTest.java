package it.szyszka.user.service;

import it.szyszka.user.model.User;
import it.szyszka.user.repository.UserRepository;
import it.szyszka.utils.server.Response;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

/**
 * Created by rafal on 27.12.17.
 */
@ActiveProfiles("userRepoTest")
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService service;

    @Autowired
    private UserRepository userRepository;

    private User testUser = new User("Test", "Test User", "test@email.com", "test");

    @Test
    public void emailAndNicknameAvailable_signUp_shouldReturnUserSaved() {

        when(userRepository.findByEmail(anyString()))
                .thenReturn(null);

        when(userRepository.findByNickname(anyString()))
                .thenReturn(null);

        doNothing().when(userRepository.save((User) any()));

        Response response = service.signUp(testUser);

        Assert.assertEquals(Response.USER_SAVED, response);

    }

    @Test
    public void emailTaken_signUp_shouldReturnEmailTaken() {

        when(userRepository.findByEmail(anyString()))
                .thenReturn(testUser);

        Response response = service.signUp(testUser);

        Assert.assertEquals(Response.EMAIL_TAKEN, response);

    }

    @Test
    public void nicknameTaken_signUp_shouldReturnNicknameTaken() {

        when(userRepository.findByEmail(anyString()))
                .thenReturn(null);

        when(userRepository.findByNickname(anyString()))
                .thenReturn(testUser);

        Response response = service.signUp(testUser);

        Assert.assertEquals(Response.NICKNAME_TAKEN, response);

    }

}
