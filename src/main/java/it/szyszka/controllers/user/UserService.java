package it.szyszka.controllers.user;

import it.szyszka.datamodel.server.MailServiceImpl;
import it.szyszka.datamodel.server.ServerResponseCode;
import it.szyszka.datamodel.server.mails.EmailVerificationMessage;
import it.szyszka.datamodel.user.User;
import it.szyszka.datamodel.user.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.internet.AddressException;
import java.util.Set;

/**
 * Created by rafal on 08.09.17.
 */
@Service
public class UserService {

    private Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    UserRepository userRepo;

    public ServerResponseCode saveUser(User user) {
        if(userRepo.findByEmail(user.getEmail()) == null) {
            if(userRepo.findByNickname(user.getNickname()) == null) {
                userRepo.save(user);
                return ServerResponseCode.USER_SAVED;
            } else return ServerResponseCode.NICKNAME_TAKEN;
        } else return ServerResponseCode.EMAIL_TAKEN;
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public Set<UserDTO> aTrustsB(User user, String toTrustEmail) throws UserNotFoundException {
        User toTrust = userRepo.findByEmail(toTrustEmail);
        if(toTrust != null) {
            logger.info("User: " + user.getEmail() + " trusts " + toTrust.getEmail());
            user.trusts(toTrust);
        } else {
            logger.error("Couldn't find user: " + toTrustEmail + " in database.");
            throw new UserNotFoundException("Couldn't find user: " + toTrustEmail + " in database.");
        }

        userRepo.save(user);
        return UserDTO.convertToSimpleSet(user.getTrusted());
    }

    public UserDTO updateUserPassword(String userEmail, String password) {
        return UserDTO.getSimpleUser(userRepo.updateUserPassword(userEmail, password));
    }

    public UserDTO updateUserDetails(UserDTO.Details details) {
        return UserDTO.getSimpleUser(userRepo.updateUserDetails(
                details.getEmail(), details.getCity(), details.getMailingAddress(), details.getPhoneNumber(),
                details.getAbout(), details.getPreferredContact()
            ));
    }

    public Set<UserDTO> makeFriends(User user, String friendEmail) throws UserNotFoundException {
        User friend = userRepo.findByEmail(friendEmail);
        if(friend != null) {
            logger.info("User: " + user.getEmail() + " knows " + friend.getEmail());
            user.knows(friend);
        } else {
            logger.error("Couldn't find user: " + friendEmail + " in database.");
            throw new UserNotFoundException("Couldn't find user: " + friendEmail + " in database.");
        }

        userRepo.save(user);
        return UserDTO.convertToSimpleSet(user.getFriends());
    }

}
