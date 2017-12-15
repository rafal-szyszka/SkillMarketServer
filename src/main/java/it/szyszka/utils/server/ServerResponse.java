package it.szyszka.utils.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by rafal on 26.09.17.
 */
@AllArgsConstructor
@NoArgsConstructor
public abstract class ServerResponse {

    @Getter @Setter private Response code;

}
