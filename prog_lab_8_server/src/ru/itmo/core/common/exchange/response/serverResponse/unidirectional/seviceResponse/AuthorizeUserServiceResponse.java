package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse;

import ru.itmo.core.common.exchange.Client;



public class AuthorizeUserServiceResponse extends ServiceResponse {


    private boolean authorized;
    private String message;



    public AuthorizeUserServiceResponse(Client client, boolean authorized) {
        super(client);
        this.authorized = authorized;
    }


    public AuthorizeUserServiceResponse(Client client, boolean authorized, String message) {
        super(client);
        this.authorized = authorized;
        this.message = message;
    }




    public boolean isAuthorized() {
        return authorized;
    }


    public String getMessage() {
        return message;
    }



}
