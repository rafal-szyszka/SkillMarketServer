package it.szyszka.messages.model;

import it.szyszka.user.model.User;
import lombok.*;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@RelationshipEntity(type = "SEND")
public class SendMessage {
    @GraphId @Getter @Setter Long id;

    @NonNull @Getter @Setter @StartNode User sender;
    @NonNull @Getter @Setter @EndNode Message message;
    @NonNull @Getter @Setter String recipientFullName;

    public SendMessage(String recipientFullName) {
        this.recipientFullName = recipientFullName;
    }

}
