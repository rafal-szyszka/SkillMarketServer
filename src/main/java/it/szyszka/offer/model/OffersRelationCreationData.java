package it.szyszka.offer.model;

import lombok.Data;

/**
 * Created by rafal on 06.12.17.
 */
@Data
public class OffersRelationCreationData {

    private Long advertiserId;
    private Advertisement advertisement;
    private Advertisement.Character character;

}
