package it.szyszka.datamodel.messages;

import it.szyszka.datamodel.user.User;
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
    @NonNull @Getter @Setter Long senderID;

    public ReceivedMessage(Long senderID){
        this.senderID = senderID;
    }

}
