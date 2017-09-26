package it.szyszka.controllers.messages;

import it.szyszka.datamodel.messages.Message;
import it.szyszka.datamodel.messages.ReceivedMessage;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rafal on 18.09.17.
 */
@Repository
public interface ReceivedMessageRepository extends GraphRepository<ReceivedMessage> {

    @Query("MATCH (n:User) -[:RECEIVED]-> (m:Message) WHERE ID(n)={userId} RETURN m")
    List<Message> findAllReceivedMessages(@Param("userId") Long userId);

    @Query("MATCH (u:User) -[r:RECEIVED]-> (m:Message) WHERE ID(m)={messageId} and ID(u)={userId} DETACH DELETE r")
    void deleteReceivedRelationByUserIdAndMessageId(
            @Param("messageId") Long messageId,
            @Param("userId") Long userId
    );

    @Query("MATCH (u:User) -[r:RECEIVED]-> (m:Message) WHERE ID(m)={messageId} and ID(u)={userId} return r")
    ReceivedMessage findReceivedMessageByUserIdAndMessageId(
            @Param("messageId") Long messageId,
            @Param("userId") Long userId
    );

}
