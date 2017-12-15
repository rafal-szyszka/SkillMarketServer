package it.szyszka.messages.controller;

import it.szyszka.messages.service.MailboxService;
import it.szyszka.messages.service.Postman;
import it.szyszka.user.service.UserService;
import it.szyszka.messages.model.PreSendMessage;
import it.szyszka.messages.model.ProofOfPosting;
import it.szyszka.messages.model.ReceivedMessage;
import it.szyszka.messages.model.SendMessage;
import it.szyszka.user.model.User;
import it.szyszka.messages.service.Mailbox;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by rafal on 10.09.17.
 */
@RestController
@RequestMapping("api/messages")
public class MessagesApiController {

    static Logger logger = Logger.getLogger(MessagesApiController.class);

    @Autowired private MailboxService messageService;
    @Autowired private UserService userService;
    @Autowired private Postman postman;

    @RequestMapping(value = "/action/send", method = RequestMethod.POST)
    public ResponseEntity<ProofOfPosting> sendMessage(@RequestBody PreSendMessage preMessage) {
        User sender = userService.findUserById(preMessage.getSenderID());
        User recipient = userService.findUserById(preMessage.getRecipientID());
        ProofOfPosting proofOfPosting = new ProofOfPosting(preMessage, "FAILED", "");

        if(recipient != null) {
            messageService.saveMessageRelations(
                    postman.deliverAndReceiveMessage(
                            sender,
                            recipient,
                            preMessage.getMessage()
                    )
            );
            proofOfPosting.setStatus("SEND");
            proofOfPosting.setRecipientEmail(recipient.getEmail());

            logger.info(proofOfPosting.toSuccessString());

            return new ResponseEntity<>(proofOfPosting, HttpStatus.OK);
        }

        logger.warn(proofOfPosting.toFailedString());
        return new ResponseEntity<>(proofOfPosting, HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/action/delete/send", method = RequestMethod.GET)
    public ResponseEntity<Void> deleteSendMessage(@RequestParam Long userId, @RequestParam Long messageId) {
        messageService.deleteSendByIdAndSenderId(messageId, userId);
        SendMessage relation = messageService.findSendRelationMessageByIdAndSenderId(messageId, userId);

        messageService.deleteAllFreeMessages();

        if(relation == null) {
            logger.info("Successfully deleted send message with id: " + messageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Couldn't delete send message with id: " + messageId);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/action/delete/received", method = RequestMethod.GET)
    public ResponseEntity<Void> deleteReceivedMessage(@RequestParam Long userId, @RequestParam Long messageId) {
        messageService.deleteReceivedRelationByIdAndRecipientId(messageId, userId);
        ReceivedMessage relation = messageService.findByIdAndRecipientId(messageId, userId);

        messageService.deleteAllFreeMessages();

        if(relation == null) {
            logger.info("Successfully deleted received message with id: " + messageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Couldn't delete received message with id: " + messageId);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/sync/mailbox", method = RequestMethod.GET)
    public ResponseEntity<Mailbox> userMailbox(@RequestParam Long userId) {
        Mailbox mailbox = new Mailbox(
                messageService.syncMailbox(userId)
        );

        logger.info("Synchronized users \"" + userId + "\" received and send messages.");
        return new ResponseEntity<>(
                mailbox,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/sync/inbox", method = RequestMethod.GET)
    public ResponseEntity<Mailbox> userInbox(@RequestParam Long userId) {
        Mailbox inbox = new Mailbox();
        inbox.setReceived(
                messageService.syncInbox(userId)
        );

        logger.info("Synchronized users \"" + userId + "\" received messages.");
        return new ResponseEntity<>(
                inbox,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/sync/outbox", method = RequestMethod.GET)
    public ResponseEntity<Mailbox> userOutbox(@RequestParam Long userId) {
        Mailbox outbox = new Mailbox();
        outbox.setSend(
                messageService.syncOutbox(userId)
        );

        logger.info("Synchronized users \"" + userId + "\" send messages.");
        return new ResponseEntity<>(
                outbox,
                HttpStatus.OK
        );
    }

}
