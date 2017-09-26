package it.szyszka;

import it.szyszka.controllers.messages.ReceivedMessageRepository;
import it.szyszka.controllers.messages.SendMessageRepository;
import it.szyszka.controllers.messages.MessageRepository;
import it.szyszka.controllers.user.UserRepository;
import it.szyszka.datamodel.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@SpringBootApplication
@EnableNeo4jRepositories
public class SkillMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillMarketApplication.class, args);
    }

//    @Bean
//    CommandLineRunner demo(UserRepository userRepo, MessageRepository messageRepo,
//                           SendMessageRepository sendRepo, ReceivedMessageRepository receivedRepo) { return args -> {
//
//            messageRepo.deleteAll();
//            userRepo.deleteAll();
//        };
//    }

    @Configuration
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        UserRepository userRepository;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService());
        }

        @Bean
        UserDetailsService userDetailsService(){
            return new UserDetailsService() {
                @Override
                public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
                    User person = userRepository.findByEmail(userEmail);
                    if (person != null) {
                        return new org.springframework.security.core.userdetails.User(person.getEmail(),
                                person.getPassword(),
                                true, true, true, true,
                                AuthorityUtils.createAuthorityList("USER"));
                    } else {
                        throw new UsernameNotFoundException
                                ("Could not find the user '" +userEmail + "'");
                    }
                }
            };
        }
    }

    @EnableWebSecurity
    @Configuration
    class WebSecurityConfig extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .antMatchers("/api/user/reg").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic()
                .and()
                    .csrf().disable();
        }

    }

}