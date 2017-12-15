package it.szyszka.offer.model;

import it.szyszka.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

import javax.validation.constraints.NotNull;

/**
 * Created by rafal on 06.12.17.
 */
@Data
@NoArgsConstructor
@RelationshipEntity(type = "OBSERVES")
public class ObserveRelation {

    @GraphId
    private Long id;

    @NotNull
    @StartNode
    private User observer;

    @NotNull
    @EndNode
    private Advertisement advertisement;

    public ObserveRelation(User observer, Advertisement advertisement) {
        this.observer = observer;
        this.advertisement = advertisement;
    }
}
