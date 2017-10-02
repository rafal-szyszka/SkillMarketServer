package it.szyszka.controllers.security;

import it.szyszka.controllers.user.UserRepository;
import it.szyszka.datamodel.server.MailServiceImpl;
import it.szyszka.datamodel.server.Response;
import it.szyszka.datamodel.server.mails.EmailVerificationMessage;
import it.szyszka.datamodel.server.security.HashGenerator;
import it.szyszka.datamodel.server.security.VerificationToken;
import it.szyszka.datamodel.user.User;
import it.szyszka.datamodel.user.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;

/**
 * Created by rafal on 28.09.17.
 */
@Service
public class SecurityService {

    private static Logger logger = Logger.getLogger(SecurityService.class);

    @Autowired private MailServiceImpl mailService;
    @Autowired private TokenRepository tokenRepo;

    public void createEmailVerificationRequest(User user) {
        VerificationToken token = new VerificationToken(
                HashGenerator.generateSHA512Key(user.getEmail()),
                user
        );
        tokenRepo.save(token);
        sendEmailVerificationMessage(user.getFullName(), user.getEmail(), token.getToken());
    }

    public Response verifyToken(String userToken, UserRepository userRepo) {
        VerificationToken token = tokenRepo.findVerificationTokenByToken(userToken);
        if(token != null) {
            return activateUserAccount(token, userRepo);
        } else {
            return Response.TOKEN_NOT_FOUND;
        }
    }

    private Response activateUserAccount(VerificationToken token, UserRepository userRepo) {
        User user = token.getInactiveUser();
        user.setStatus(UserDTO.ActivationStatus.ACTIVE);
        userRepo.save(user);
        tokenRepo.delete(token);
        return Response.USER_ACCOUNT_ACTIVATED;
    }

    private void sendEmailVerificationMessage(String fullName, String email, String token) {
        try {
            EmailVerificationMessage msg = EmailVerificationMessage.createMessage(fullName, email, token);
            mailService.sendMail(msg);
            logger.info("Successfully send message to: " + email);
        } catch (AddressException e) {
            logger.error("Failed to send verify mail to: " + email + "\n");
            e.printStackTrace();
        }
    }

}
