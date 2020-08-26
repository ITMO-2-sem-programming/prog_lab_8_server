package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background;


import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.ServiceResponse;

public abstract class BackgroundServiceResponse extends ServiceResponse {

    public BackgroundServiceResponse(Client client, CRStatus status, String message) {
        super(client, status, message);
    }
}
