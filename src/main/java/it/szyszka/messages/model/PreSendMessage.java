package it.szyszka.messages.model;

import lombok.*;

/**
 * Created by rafal on 18.09.17.
 */
@AllArgsConstructor
@NoArgsConstructor
public class PreSendMessage {

    @Getter @Setter private Long senderID;
    @Getter @Setter private Long recipientID;
    @Getter @Setter private Message message;

    public PreSendMessage(PreSendMessage preSendMessage) {
        senderID = preSendMessage.getSenderID();
        recipientID = preSendMessage.getRecipientID();
        message = preSendMessage.getMessage();
    }

}
