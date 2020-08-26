package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.UnidirectionalResponse;

public abstract class UserCommandResponse extends UnidirectionalResponse {
    public UserCommandResponse(Client client, CRStatus status, String message) {
        super(client, status, message);
    }
}
