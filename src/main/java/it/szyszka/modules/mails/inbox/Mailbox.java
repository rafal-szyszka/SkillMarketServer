package it.szyszka.modules.mails.inbox;

import it.szyszka.datamodel.messages.Message;
import it.szyszka.datamodel.messages.ReceivedMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Created by rafal on 18.09.17.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Mailbox {

    @Getter @Setter private List<Message> received;
    @Getter @Setter private List<Message> send;

    public void receiveMail(Message newMessage) {
        received.add(newMessage);
    }

    public void deleteMail(Message message) {
        received.remove(message);
    }

}
