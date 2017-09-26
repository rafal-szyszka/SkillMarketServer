package it.szyszka.controllers.user;

/**
 * Created by rafal on 26.09.17.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

}
