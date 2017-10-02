package it.szyszka.controllers.security;

import it.szyszka.datamodel.server.security.VerificationToken;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by rafal on 28.09.17.
 */
public interface TokenRepository extends GraphRepository<VerificationToken> {

    VerificationToken findVerificationTokenByToken(String token);
}
