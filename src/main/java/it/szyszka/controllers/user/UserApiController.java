package it.szyszka.controllers.user;

import it.szyszka.datamodel.server.ServerResponse;
import it.szyszka.datamodel.server.ServerResponseCode;
import it.szyszka.datamodel.server.ServerSetResponse;
import it.szyszka.datamodel.user.User;
import it.szyszka.datamodel.user.UserDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Created by rafal on 08.09.17.
 */
@RestController
@RequestMapping("api/user")
public class UserApiController {

    static Logger logger = Logger.getLogger(UserApiController.class);

    @Autowired
    UserService userService;

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<ServerResponseCode> signUp(@RequestBody User user) {
        ServerResponseCode status = ServerResponseCode.NO_RESPONSE;
        if(user != null) {
            status = userService.saveUser(user);
        }

        return new ResponseEntity<>(
                status,
                status == ServerResponseCode.USER_SAVED ? HttpStatus.OK : HttpStatus.BAD_REQUEST
        );
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<UserDTO> signIn(@RequestParam String userEmail) {
        logger.info(userEmail + " signed in.");

        UserDTO signedInUser = new UserDTO(
                userService.findUserByEmail(userEmail)
        );

        return new ResponseEntity<>(signedInUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/trust", method = RequestMethod.POST)
    public ResponseEntity<ServerSetResponse<UserDTO>> trustUser(@RequestParam String userEmail, @RequestParam String toTrustEmail){
        User user = userService.findUserByEmail(userEmail);
        Set<User> userTrusted = user.getTrusted();
        try {
            userTrusted = userService.aTrustsB(user, toTrustEmail);
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            ServerResponseCode.SUCCESSFULLY_CREATED_TRUST_RELATION,
                            UserDTO.convertToSimpleSet(userTrusted)
                    ),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            logger.fatal(e.getMessage());
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            ServerResponseCode.FAILED_TO_CREATE_TRUST_RELATION,
                            UserDTO.convertToSimpleSet(userTrusted)
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @RequestMapping(value = "/makeFriends", method = RequestMethod.POST)
    public ResponseEntity<ServerSetResponse<UserDTO>> makeFriends(@RequestParam String userEmail, @RequestParam String friendEmail) {
        User user = userService.findUserByEmail(userEmail);
        Set<User> userFriends = user.getFriends();

        try {
            userFriends = userService.makeFriends(user, friendEmail);
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            ServerResponseCode.SUCCESSFULLY_CREATED_FRIEND_RELATION,
                            UserDTO.convertToSimpleSet(userFriends)
                    ),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            logger.fatal(e.getMessage());
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            ServerResponseCode.FAILED_TO_CREATE_FRIEND_RELATION,
                            UserDTO.convertToSimpleSet(userFriends)
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

}
