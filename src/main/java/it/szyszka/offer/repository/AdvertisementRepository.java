package it.szyszka.offer.repository;

import it.szyszka.offer.model.Advertisement;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by rafal on 06.12.17.
 */
@Repository
public interface AdvertisementRepository extends GraphRepository<Advertisement> {
}
