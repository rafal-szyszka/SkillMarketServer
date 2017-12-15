package it.szyszka.messages.model;

import it.szyszka.user.model.User;
import lombok.*;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@RelationshipEntity(type = "RECEIVED")
public class ReceivedMessage {
    @GraphId @Getter @Setter Long id;

    @NonNull @Getter @Setter @StartNode User recipient;
    @NonNull @Getter @Setter @EndNode Message message;
    @NonNull @Getter @Setter String senderFullName;

    public ReceivedMessage(String senderFullName) {
        this.senderFullName = senderFullName;
    }
}
