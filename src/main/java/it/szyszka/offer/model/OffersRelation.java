package it.szyszka.offer.model;

import it.szyszka.user.model.User;
import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import javax.validation.constraints.NotNull;

/**
 * Created by rafal on 06.12.17.
 */
@Data
@RelationshipEntity(type = "OFFERS")
public class OffersRelation {

    @GraphId
    private Long id;

    @StartNode
    @NotNull
    private User advertiser;

    @EndNode
    @NotNull
    private Advertisement advertisement;

    @NotNull
    private Advertisement.Character character;
}
