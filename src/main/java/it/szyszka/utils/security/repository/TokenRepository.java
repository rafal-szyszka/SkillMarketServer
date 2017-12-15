package it.szyszka.utils.security.repository;

import it.szyszka.utils.security.model.VerificationToken;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * Created by rafal on 28.09.17.
 */
public interface TokenRepository extends GraphRepository<VerificationToken> {

    VerificationToken findVerificationTokenByToken(String token);
}
