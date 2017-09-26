package it.szyszka.datamodel.messages;

import it.szyszka.datamodel.user.User;
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
    @NonNull @Getter @Setter Long recipientID;

    public SendMessage(Long recipientID) {
        this.recipientID = recipientID;
    }

}
