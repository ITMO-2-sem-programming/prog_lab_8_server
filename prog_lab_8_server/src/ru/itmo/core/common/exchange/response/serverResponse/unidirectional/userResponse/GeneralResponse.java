package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse;


import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;

public class GeneralResponse extends UserCommandResponse {


    public GeneralResponse(Client client, CRStatus status, String message) {
        super(client, status, message);
    }
}
