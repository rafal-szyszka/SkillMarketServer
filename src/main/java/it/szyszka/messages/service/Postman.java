package it.szyszka.messages.service;

import it.szyszka.messages.model.Message;
import it.szyszka.messages.model.MessageRelations;
import it.szyszka.messages.model.ReceivedMessage;
import it.szyszka.messages.model.SendMessage;
import it.szyszka.user.model.User;
import org.springframework.stereotype.Component;

/**
 * Created by rafal on 09.09.17.
 */
@Component
public class Postman {

    public MessageRelations deliverAndReceiveMessage(User sender, User recipient, Message message) {
        return new MessageRelations(
                new SendMessage(sender, message, recipient.getFullName()),
                new ReceivedMessage(recipient, message, sender.getFullName())
        );
    }

}
