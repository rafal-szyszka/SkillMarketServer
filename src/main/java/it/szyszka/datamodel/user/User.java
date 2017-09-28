package it.szyszka.datamodel.user;

import it.szyszka.datamodel.messages.ReceivedMessage;
import it.szyszka.datamodel.messages.SendMessage;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by rafal on 27.08.17.
 */
@NodeEntity
public class User {

    @GraphId @Getter @Setter private Long id;

    @NonNull @Getter @Setter private String nickname;
    @NonNull @Getter @Setter private String fullName;

    @NonNull @Getter @Setter private String email;
    @NonNull @Getter @Setter private String password;

    @NonNull @Getter @Setter private String city;
    @NonNull @Getter @Setter private UserDTO.ActivationStatus status = UserDTO.ActivationStatus.INACTIVE;

    @Getter @Setter private String mailingAddress;
    @Getter @Setter private String phoneNumber;

    @Getter @Setter private String about;
    @Getter @Setter private String preferredContact;

    @Relationship(type = "KNOWS", direction = Relationship.UNDIRECTED)
    @Getter @Setter private Set<User> friends;

    public void knows(User user) {
        if(friends == null) {
            friends = new HashSet<>();
        }
        friends.add(user);
    }

    @Relationship(type = "TRUSTS")
    @Getter @Setter private Set<User> trusted;

    public void trusts(User user) {
        if(trusted == null) {
            trusted = new HashSet<>();
        }
        trusted.add(user);
    }

//    @Relationship(type = "SEND")
//    private @Getter @Setter Set<SendMessage> sendMessages;
//
//    @Relationship(type = "RECEIVED")
//    private @Getter @Setter Set<ReceivedMessage> receivedMessages;

    public User() {
        friends = new HashSet<>();
        trusted = new HashSet<>();
    }

    public User(String nickname, String fullName, String email, String password) {
        this.nickname = nickname;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.friends = new HashSet<>();
        this.trusted = new HashSet<>();
    }
}

