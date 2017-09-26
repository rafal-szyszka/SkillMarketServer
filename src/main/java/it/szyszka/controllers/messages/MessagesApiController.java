package it.szyszka.controllers.messages;

import it.szyszka.controllers.user.UserRepository;
import it.szyszka.datamodel.messages.PreSendMessage;
import it.szyszka.datamodel.messages.ProofOfPosting;
import it.szyszka.datamodel.messages.SendMessage;
import it.szyszka.datamodel.user.User;
import it.szyszka.modules.mails.inbox.Mailbox;
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

    @Autowired private MailboxService service;
    @Autowired private UserRepository userRepo;
    @Autowired private MessageRepository messageRepo;
    @Autowired private ReceivedMessageRepository receivedRepo;
    @Autowired private SendMessageRepository sendRepo;
    @Autowired private Postman postman;

    @RequestMapping(value = "/action/send", method = RequestMethod.POST)
    public ResponseEntity<ProofOfPosting> sendMessage(@RequestBody PreSendMessage preMessage) {
        User sender = userRepo.findOne(preMessage.getSenderID());
        User recipient = userRepo.findOne(preMessage.getRecipientID());
        ProofOfPosting proofOfPosting = new ProofOfPosting(preMessage, "FAILED", "");

        if(recipient != null) {
            service.saveMessageRelations(
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
        sendRepo.deleteSendRelationByUserIdAndMessageId(messageId, userId);
        SendMessage relation = sendRepo.findSendMessageByUserIdAndMessageId(messageId, userId);

        messageRepo.deleteAllMessagesWithNoRelations();

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
        receivedRepo.deleteReceivedRelationByUserIdAndMessageId(messageId, userId);
        SendMessage relation = sendRepo.findSendMessageByUserIdAndMessageId(messageId, userId);

        messageRepo.deleteAllMessagesWithNoRelations();

        if(relation == null) {
            logger.info("Successfully deleted received message with id: " + messageId);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            logger.warn("Couldn't delete received message with id: " + messageId);
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @RequestMapping(value = "/mailbox", method = RequestMethod.GET)
    public ResponseEntity<Mailbox> userMailbox(@RequestParam Long userId) {
        Mailbox mailbox = new Mailbox();
        mailbox.setReceived(receivedRepo.findAllReceivedMessages(userId));
        mailbox.setSend(sendRepo.findAllSendMessages(userId));

        logger.info("Loaded users \"" + userId + "\" received and send messages.");
        return new ResponseEntity<>(
                mailbox,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/sync/inbox", method = RequestMethod.GET)
    public ResponseEntity<Mailbox> userInbox(@RequestParam Long userId) {
        Mailbox inbox = new Mailbox();
        inbox.setReceived(receivedRepo.findAllReceivedMessages(userId));

        logger.info("Synchronized users \"" + userId + "\" received messages.");
        return new ResponseEntity<>(
                inbox,
                HttpStatus.OK
        );
    }

    @RequestMapping(value = "/sync/outbox", method = RequestMethod.GET)
    public ResponseEntity<Mailbox> userOutbox(@RequestParam Long userId) {
        Mailbox outbox = new Mailbox();
        outbox.setSend(sendRepo.findAllSendMessages(userId));

        logger.info("Synchronized users \"" + userId + "\" send messages.");
        return new ResponseEntity<>(
                outbox,
                HttpStatus.OK
        );
    }

}
