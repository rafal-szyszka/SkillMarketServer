package it.szyszka.utils.security.model;

import it.szyszka.user.model.User;
import lombok.*;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by rafal on 28.09.17.
 */
@NodeEntity
@RequiredArgsConstructor
@NoArgsConstructor
public class VerificationToken {

    @GraphId @Getter @Setter private Long id;

    @NonNull @Getter @Setter private String token;

    @Relationship(type = "HAS_TO_VERIFY", direction = Relationship.INCOMING)
    @NonNull @Getter @Setter private User inactiveUser;

}
