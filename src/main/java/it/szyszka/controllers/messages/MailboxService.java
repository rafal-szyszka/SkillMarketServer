package it.szyszka.controllers.messages;

import it.szyszka.datamodel.messages.Message;
import it.szyszka.datamodel.messages.MessageRelations;
import it.szyszka.datamodel.messages.ReceivedMessage;
import it.szyszka.datamodel.messages.SendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by rafal on 10.09.17.
 */
@Service
public class MailboxService {

    @Autowired private SendMessageRepository sendRepo;
    @Autowired private ReceivedMessageRepository receivedRepo;
    @Autowired private MessageRepository messageRepo;

    public enum MailboxType {
        MAILBOX, INBOX, OUTBOX
    }

    public void saveMessageRelations(MessageRelations relations) {
        sendRepo.save(relations.getSend());
        receivedRepo.save(relations.getReceived());
    }

    public SendMessage findSendRelationMessageByIdAndSenderId(Long senderId, Long id) {
        return sendRepo.findByIdAndSenderId(id, senderId);
    }

    public void deleteSendByIdAndSenderId(Long id, Long senderId){
        sendRepo.deleteByIdAndUserId(id, senderId);
    }

    public void deleteAllFreeMessages() {
        messageRepo.deleteAllMessagesWithNoRelations();
    }

    public void deleteReceivedRelationByIdAndRecipientId(Long id, Long recipientId){
        receivedRepo.deleteByIdAndRecipientId(id, recipientId);
    }

    public ReceivedMessage findByIdAndRecipientId(Long id, Long userId) {
        return receivedRepo.findByIdAndRecipientId(id, userId);
    }

    public Pair<List<Message>, List<Message>> syncMailbox(Long userId) {
        return Pair.of(
                receivedRepo.findAllReceivedMessagesByUserId(userId),
                sendRepo.findAllSendMessagesByUserId(userId)
        );
    }

    public List<Message> syncMailboxyType(Long userId, MailboxType type) {
        switch(type) {
            case INBOX: {
                return receivedRepo.findAllReceivedMessagesByUserId(userId);
            }
            case OUTBOX: {
                return receivedRepo.findAllReceivedMessagesByUserId(userId);
            }
            default: {
                throw new IllegalArgumentException("Mailbox type not supported: " + type);
            }
        }
    }
}
