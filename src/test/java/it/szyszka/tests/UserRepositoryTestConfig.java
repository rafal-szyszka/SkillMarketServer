package it.szyszka.tests;

import it.szyszka.user.repository.UserRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Created by rafal on 27.12.17.
 */
@Profile("userRepoTests")
@Configuration
public class UserRepositoryTestConfig {

    @Bean
    @Primary
    public UserRepository userRepository() {
        return Mockito.mock(UserRepository.class);
    }

}
