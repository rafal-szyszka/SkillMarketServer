package it.szyszka.offer.service;

import it.szyszka.offer.model.*;
import it.szyszka.offer.repository.AdvertisementRepository;
import it.szyszka.offer.repository.ObserveRelationRepository;
import it.szyszka.offer.repository.OffersRelationRepository;
import it.szyszka.user.model.User;
import it.szyszka.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafal on 06.12.17.
 */
@Service
public class AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Autowired
    private ObserveRelationRepository observeRelationRepository;

    @Autowired
    private OffersRelationRepository offersRelationRepository;

    @Autowired
    private UserService userService;

    public OffersRelation createAdvertisement(OffersRelationCreationData advertisementData) {

        OffersRelation offersRelation = parseOffersRelationCreationData(advertisementData);

        return offersRelationRepository.save(offersRelation);
    }

    private OffersRelation parseOffersRelationCreationData(OffersRelationCreationData advertisementData) {
        OffersRelation relation = new OffersRelation();
        relation.setAdvertiser(userService.findUserById(advertisementData.getAdvertiserId()));
        relation.setAdvertisement(advertisementData.getAdvertisement());
        relation.setCharacter(advertisementData.getCharacter());
        return relation;
    }

    public void observeAdvertisement(Long observerId, Long advertisementId) {
        User observer = userService.findUserById(observerId);
        Advertisement advertisement = findById(advertisementId);
        ObserveRelation observeRelation = new ObserveRelation(observer, advertisement);
        observeRelationRepository.save(observeRelation);
    }

    public Advertisement findById(Long advertisementId) {
        return advertisementRepository.findOne(advertisementId);
    }

    public Advertisement updateAdvertisement(Long advertisementId, Advertisement advertisement) {
        advertisement.setId(advertisementId);
        advertisementRepository.save(advertisement);
        return advertisement;
    }

    public List<OffersRelationDTO> getAllOffersRelations() {
        List<OffersRelationDTO> offersRelationDTOList = new ArrayList<>();
        Iterable<OffersRelation> offersRelations = offersRelationRepository.findAll();

        offersRelations.forEach(
                offersRelation -> offersRelationDTOList.add(new OffersRelationDTO(offersRelation))
        );

        return offersRelationDTOList;
    }
}
