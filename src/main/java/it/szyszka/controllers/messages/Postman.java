package it.szyszka.controllers.messages;

import it.szyszka.datamodel.messages.Message;
import it.szyszka.datamodel.messages.MessageRelations;
import it.szyszka.datamodel.messages.ReceivedMessage;
import it.szyszka.datamodel.messages.SendMessage;
import it.szyszka.datamodel.user.User;
import org.springframework.stereotype.Component;

/**
 * Created by rafal on 09.09.17.
 */
@Component
public class Postman {

    public MessageRelations deliverAndReceiveMessage(User sender, User recipient, Message message) {
        return new MessageRelations(
                new SendMessage(sender, message, recipient.getId()),
                new ReceivedMessage(recipient, message, sender.getId())
        );
    }

}
