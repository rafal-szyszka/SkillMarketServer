package it.szyszka.controllers.user;

import it.szyszka.datamodel.user.User;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rafal on 08.09.17.
 */
@Repository
public interface UserRepository extends GraphRepository<User> {

    User findByEmail(String email);
    User findByNickname(String nickname);

}
