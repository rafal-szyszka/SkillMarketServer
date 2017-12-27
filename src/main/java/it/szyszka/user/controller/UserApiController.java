package it.szyszka.user.controller;

import it.szyszka.security.service.SecurityService;
import it.szyszka.utils.server.Response;
import it.szyszka.utils.server.ServerSetResponse;
import it.szyszka.user.model.User;
import it.szyszka.user.model.UserDTO;
import it.szyszka.user.service.UserService;
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

    @Autowired private UserService userService;
    @Autowired private SecurityService securityService;

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public ResponseEntity<Response> signUp(@RequestBody User user) {
        Response status = Response.NO_RESPONSE;
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        if(user != null) {
            status = userService.signUp(user);
        }

        if(status == Response.USER_SAVED) {
            securityService.createEmailVerificationRequest(user);
            httpStatus = HttpStatus.OK;
        }

        return new ResponseEntity<>(status, httpStatus);
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
        Set<UserDTO> userTrusted = UserDTO.convertToSimpleSet(user.getTrusted());
        try {
            userTrusted = userService.aTrustsB(user, toTrustEmail);
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            Response.SUCCESSFULLY_CREATED_TRUST_RELATION,
                            userTrusted
                    ),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            logger.fatal(e.getMessage());
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            Response.FAILED_TO_CREATE_TRUST_RELATION,
                            userTrusted
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @RequestMapping(value = "/makeFriends", method = RequestMethod.POST)
    public ResponseEntity<ServerSetResponse<UserDTO>> makeFriends(@RequestParam String userEmail, @RequestParam String friendEmail) {
        User user = userService.findUserByEmail(userEmail);
        Set<UserDTO> userFriends = UserDTO.convertToSimpleSet(user.getFriends());

        try {
            userFriends = userService.makeFriends(user, friendEmail);
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            Response.SUCCESSFULLY_CREATED_FRIEND_RELATION,
                            userFriends
                    ),
                    HttpStatus.OK
            );
        } catch (UserNotFoundException e) {
            logger.fatal(e.getMessage());
            return new ResponseEntity<>(
                    new ServerSetResponse<>(
                            Response.FAILED_TO_CREATE_FRIEND_RELATION,
                            userFriends
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> updatePassword(@RequestParam String userEmail, @RequestParam String newPassword) {
        return new ResponseEntity<>(
                userService.updateUserPassword(userEmail, newPassword),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/update/details", method = RequestMethod.PUT)
    public ResponseEntity<UserDTO> updateDetails(@RequestBody UserDTO.Details details) {
        return new ResponseEntity<>(
                userService.updateUserDetails(details),
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/get/friends", method = RequestMethod.GET)
    public ResponseEntity<Set<UserDTO>> getFriends(@RequestParam String userEmail) {
        Set<UserDTO> friends = userService.getUserFriends(userEmail);
        friends.forEach(user -> System.out.print(user.getFullName() + " "));
        return ResponseEntity.ok(friends);
    }

    @GetMapping(value = "/get/all")
    public ResponseEntity<Set<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}