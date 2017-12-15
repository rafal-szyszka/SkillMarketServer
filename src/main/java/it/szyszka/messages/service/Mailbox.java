package it.szyszka.messages.service;

import it.szyszka.messages.model.ReceivedMessage;
import it.szyszka.messages.model.SendMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.List;

/**
 * Created by rafal on 18.09.17.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Mailbox {

    @Getter @Setter private List<ReceivedMessage> received;
    @Getter @Setter private List<SendMessage> send;

    public void receiveMail(ReceivedMessage newMessage) {
        received.add(newMessage);
    }

    public void deleteMail(SendMessage message) {
        received.remove(message);
    }

    public Mailbox(Pair<List<ReceivedMessage>, List<SendMessage>> mailbox){
        received = mailbox.getFirst();
        send = mailbox.getSecond();
    }

}
