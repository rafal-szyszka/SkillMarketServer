package it.szyszka.offer.repository;

import it.szyszka.offer.model.ObserveRelation;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rafal on 06.12.17.
 */
@Repository
public interface ObserveRelationRepository extends GraphRepository<ObserveRelation> {
}
