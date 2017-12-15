package it.szyszka.offer.controller;

import it.szyszka.offer.model.Advertisement;
import it.szyszka.offer.model.OffersRelationCreationData;
import it.szyszka.offer.model.OffersRelationDTO;
import it.szyszka.offer.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Created by rafal on 06.12.17.
 */
@RestController
@RequestMapping("api/offer")
public class AdvertisementController {

    @Autowired
    private AdvertisementService advertisementService;

    @PostMapping
    public ResponseEntity<OffersRelationDTO> createAdvertisement(@RequestBody OffersRelationCreationData advertisement) {

        OffersRelationDTO offersRelation = new OffersRelationDTO(
                advertisementService.createAdvertisement(advertisement)
        );

        return ok(
                offersRelation
        );
    }

    @PostMapping(value = "/observe")
    public ResponseEntity<String> observeAdvertisement(@RequestParam Long observerId, @RequestParam Long advertisementId) {
        advertisementService.observeAdvertisement(observerId, advertisementId);
        return ok("OK");
    }

    @PutMapping(value = "/update", params = "advertisementId")
    public ResponseEntity<Advertisement> updateAdvertisement(Long advertisementId, @RequestBody Advertisement advertisement) {
        return ok(
                advertisementService.updateAdvertisement(advertisementId, advertisement)
        );
    }

    @GetMapping
    public ResponseEntity<List<OffersRelationDTO>> getAllAdvertisementsWithTheirAdvertisers() {
        return ok(
                advertisementService.getAllOffersRelations()
        );
    }

}
