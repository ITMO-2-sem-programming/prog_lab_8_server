package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse;


import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.UnidirectionalResponse;

public abstract class ServiceResponse extends UnidirectionalResponse {

    public ServiceResponse(Client client, CRStatus status, String message) {
        super(client, status, message);
    }
}
