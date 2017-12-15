package it.szyszka.messages.repository;

import it.szyszka.messages.model.Message;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by rafal on 09.09.17.
 */
@Repository
public interface MessageRepository extends GraphRepository<Message> {

    @Query("MATCH (m:Message) WHERE ID(m)={messageId} DETACH DELETE m")
    void deleteById(@Param("messageId") Long messageId);

    @Query("MATCH (m:Message) WHERE NOT (m) <-[:RECEIVED]- () AND NOT (m) <-[:SEND]- () DELETE m")
    void deleteAllMessagesWithNoRelations();

}
