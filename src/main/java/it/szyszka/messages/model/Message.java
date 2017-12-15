package it.szyszka.messages.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

/**
 * Created by rafal on 08.09.17.
 */
@Data
@NodeEntity
@RequiredArgsConstructor
@NoArgsConstructor
public class Message {

    @GraphId private Long id;

    @NonNull private String title;
    @NonNull private String content;

}
