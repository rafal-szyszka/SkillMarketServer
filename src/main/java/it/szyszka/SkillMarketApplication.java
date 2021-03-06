package it.szyszka;

import it.szyszka.user.repository.UserRepository;
import it.szyszka.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Properties;

@SpringBootApplication
@EnableNeo4jRepositories
public class SkillMarketApplication {

    public static void main(String[] args) {
        SpringApplication.run(SkillMarketApplication.class, args);
    }

//    @Autowired
//    MailServiceImpl mailService;
//
//    @Bean
//    CommandLineRunner demo(UserRepository repo) {
//        return args -> {
//            EmailVerificationMessage msg = EmailVerificationMessage.createMessage(repo.findByEmail("rafal.sz.49@gmail.com"));
//            msg.setContent(msg.write());
//            mailService.sendMail(msg);
//        };
//    }

    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername("skill.trade.verify@gmail.com");
        mailSender.setPassword("skilltrade1234");

        Properties prop = new Properties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.transport.protocol", "smtp");

        mailSender.setJavaMailProperties(prop);
        return mailSender;
    }

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
            return userEmail -> {
                User person = userRepository.findByEmail(userEmail);
                if (person != null) {
                    return new org.springframework.security.core.userdetails.User(person.getEmail(),
                            person.getPassword(),
                            true, true, true, true,
                            AuthorityUtils.createAuthorityList("USER"));
                } else {
                    throw new UsernameNotFoundException
                            ("Could not find the user '" + userEmail + "'");
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
                    .antMatchers("/api/user/reg", "/api/user/verifyEmail").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .httpBasic()
                .and()
                    .csrf().disable();
        }

    }

}