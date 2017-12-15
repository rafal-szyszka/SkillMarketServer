package it.szyszka.offer.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by rafal on 06.12.17.
 */
@Data
@NodeEntity
public class Advertisement {

    @GraphId
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String category;

    @NotNull
    @Size(min = 20, max = 160)
    private String shortDescription;

    @Size(min = 20, max = 10000)
    private String detailDescription;

    @NotNull
    private String payment;

    @NotNull
    private Status status;

    public enum Status {
        LIVE, OUTDATED, ARCHIVAL
    }

    public enum Character {
        CONTRACTOR, PAYMASTER
    }
}
