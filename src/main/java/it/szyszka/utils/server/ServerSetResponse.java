package it.szyszka.utils.server;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Created by rafal on 26.09.17.
 */
public class ServerSetResponse<T> extends ServerResponse {

    @Getter @Setter private Set<T> setResponse;

    public ServerSetResponse(Response code, Set<T> setResponse) {
        super(code);
        this.setResponse = setResponse;
    }

}
