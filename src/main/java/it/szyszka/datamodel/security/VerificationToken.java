package it.szyszka.datamodel.security;

import it.szyszka.datamodel.user.User;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Created by rafal on 28.09.17.
 */
@NodeEntity
@RequiredArgsConstructor
public class VerificationToken {

    @GraphId @Getter @Setter private Long id;

    @NonNull @Getter @Setter private String token;

    @Relationship(type = "HAS_TO_VERIFY", direction = Relationship.INCOMING)
    @NonNull @Getter @Setter private User inactiveUser;

}
