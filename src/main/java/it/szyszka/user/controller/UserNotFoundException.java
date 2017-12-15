package it.szyszka.user.controller;

/**
 * Created by rafal on 26.09.17.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
        super(message);
    }

}
