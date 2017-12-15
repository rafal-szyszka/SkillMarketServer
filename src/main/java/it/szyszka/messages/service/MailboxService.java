package it.szyszka.messages.service;

import it.szyszka.messages.model.Message;
import it.szyszka.messages.model.MessageRelations;
import it.szyszka.messages.model.ReceivedMessage;
import it.szyszka.messages.model.SendMessage;
import it.szyszka.messages.repository.MessageRepository;
import it.szyszka.messages.repository.ReceivedMessageRepository;
import it.szyszka.messages.repository.SendMessageRepository;
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

    public Pair<List<ReceivedMessage>, List<SendMessage>> syncMailbox(Long userId) {
        return Pair.of(
                receivedRepo.findAllReceivedMessagesByRecipientId(userId),
                sendRepo.findAllSendMessagesBySenderId(userId)
        );
    }

    public List<ReceivedMessage> syncInbox(Long userId) {
        return receivedRepo.findAllReceivedMessagesByRecipientId(userId);
    }

    public List<SendMessage> syncOutbox(Long userId) {
        return sendRepo.findAllSendMessagesBySenderId(userId);
    }

    @Deprecated
    public List<Message> syncMailboxType(Long userId, MailboxType type) {
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
