package ru.itmo.core.common.exchange.response.serverResponse.unidirectional;


import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.ServerResponse;

public abstract class UnidirectionalResponse extends ServerResponse {
    public UnidirectionalResponse(Client client) {
        super(client);
    }
}
