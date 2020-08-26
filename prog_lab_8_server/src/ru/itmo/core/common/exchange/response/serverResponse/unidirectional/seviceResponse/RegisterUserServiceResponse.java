package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;

public class RegisterUserServiceResponse extends ServiceResponse {


    private boolean registered;
    private String message;



    public RegisterUserServiceResponse(Client client, CRStatus status, String message, boolean registered) {
        super(client, status, message);
        this.registered = registered;
    }


    public RegisterUserServiceResponse(Client client, CRStatus status, boolean registered) {
        super(client, status, null);
        this.registered = registered;
    }




    public boolean isRegistered() {
        return registered;
    }


    public String getMessage() {
        return message;
    }

}
