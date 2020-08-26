package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;


public class AuthorizeUserServiceResponse extends ServiceResponse {


    private final boolean authorized;



    public AuthorizeUserServiceResponse(Client client, CRStatus status, String message, boolean registered) {
        super(client, status, message);
        this.authorized = registered;
    }


    public AuthorizeUserServiceResponse(Client client, CRStatus status, boolean registered) {
        super(client, status, null);
        this.authorized = registered;
    }




    public boolean isAuthorized() {
        return authorized;
    }



}
