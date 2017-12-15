package it.szyszka.messages.repository;

import it.szyszka.messages.model.Message;
import it.szyszka.messages.model.ReceivedMessage;
import it.szyszka.messages.model.SendMessage;
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
    List<Message> findAllSendMessagesByUserId(@Param("userId") Long userId);

    @Query("MATCH (u:User) -[r:SEND]-> (m:Message) WHERE ID(u) = {userId} RETURN r, u, m")
    List<SendMessage> findAllSendMessagesBySenderId(@Param("userId") Long userId);

    @Query("MATCH (u:User) -[r:SEND]-> (m:Message) WHERE ID(m)={messageId} and ID(u)={userId} DETACH DELETE r")
    void deleteByIdAndUserId(
            @Param("messageId") Long messageId,
            @Param("userId") Long userId
    );

    @Query("MATCH (u:User) -[r:SEND]-> (m:Message) WHERE ID(m)={messageId} and ID(u)={userId} return r")
    SendMessage findByIdAndSenderId(
            @Param("messageId") Long messageId,
            @Param("userId") Long userId
    );

}
