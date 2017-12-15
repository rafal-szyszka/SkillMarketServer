package it.szyszka.offer.model;

import it.szyszka.user.model.UserDTO;
import lombok.Data;

/**
 * Created by rafal on 06.12.17.
 */
@Data
public class OffersRelationDTO {

    private Long id;
    private UserDTO advertiser;
    private Advertisement advertisement;
    private Advertisement.Character character;

    public OffersRelationDTO(OffersRelation offersRelation) {
        this.id = offersRelation.getId();
        this.advertiser = UserDTO.getSimpleUser(offersRelation.getAdvertiser());
        this.advertisement = offersRelation.getAdvertisement();
        this.character = offersRelation.getCharacter();
    }
}
