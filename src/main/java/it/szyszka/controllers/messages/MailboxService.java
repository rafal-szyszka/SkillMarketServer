package it.szyszka.controllers.messages;

import it.szyszka.datamodel.messages.MessageRelations;
import it.szyszka.datamodel.messages.ProofOfPosting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by rafal on 10.09.17.
 */
@Service
public class MailboxService {

    @Autowired private SendMessageRepository sendRepo;
    @Autowired private ReceivedMessageRepository receivedRepo;

    public void saveMessageRelations(MessageRelations relations) {
        sendRepo.save(relations.getSend());
        receivedRepo.save(relations.getReceived());
    }

}
