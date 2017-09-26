package it.szyszka.controllers.messages;

import it.szyszka.datamodel.messages.Message;
import it.szyszka.datamodel.messages.SendMessage;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by rafal on 18.09.17.
 */
@Repository
public interface SendMessageRepository extends GraphRepository<SendMessage> {

    @Query("MATCH (n:User) -[:SEND]-> (m:Message) WHERE ID(n)={userId} RETURN m")
    List<Message> findAllSendMessages(@Param("userId") Long userId);

    @Query("MATCH (u:User) -[r:SEND]-> (m:Message) WHERE ID(m)={messageId} and ID(u)={userId} DETACH DELETE r")
    void deleteSendRelationByUserIdAndMessageId(
            @Param("messageId") Long messageId,
            @Param("userId") Long userId
    );

    @Query("MATCH (u:User) -[r:SEND]-> (m:Message) WHERE ID(m)={messageId} and ID(u)={userId} return r")
    SendMessage findSendMessageByUserIdAndMessageId(
            @Param("messageId") Long messageId,
            @Param("userId") Long userId
    );

}
