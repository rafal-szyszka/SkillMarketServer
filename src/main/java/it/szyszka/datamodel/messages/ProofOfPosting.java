package it.szyszka.datamodel.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by rafal on 18.09.17.
 */
@AllArgsConstructor
public class ProofOfPosting extends PreSendMessage {

    @Getter @Setter private String status;
    @Getter @Setter private String recipientEmail;

    public ProofOfPosting(PreSendMessage preSendMessage, String status, String recipientEmail) {
        super(preSendMessage);
        this.status = status;
        this.recipientEmail = recipientEmail;
    }

    public ProofOfPosting(PreSendMessage preSendMessage){
        super(preSendMessage);
    }

    public String toSuccessString() {
        return "Susccessfully send message \"" +
                getMessage().getTitle() +
                "\" to " + recipientEmail + "\tHttp status: " + status;
    }

    public String toFailedString() {
        return "Failed to send message: " +
                getMessage().getTitle() +
                " to recipient. Probably recipient does not exists.\tHttp status: " + status;
    }

}
