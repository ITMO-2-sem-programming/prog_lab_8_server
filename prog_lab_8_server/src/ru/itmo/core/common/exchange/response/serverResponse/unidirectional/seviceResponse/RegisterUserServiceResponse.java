package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse;

import ru.itmo.core.common.exchange.Client;

public class RegisterUserServiceResponse extends ServiceResponse {


    private boolean registered;
    private String message;



    public RegisterUserServiceResponse(Client client, boolean registered) {
        super(client);
        this.registered = registered;
    }


    public RegisterUserServiceResponse(Client client, boolean registered, String message) {
        super(client);
        this.registered = registered;
        this.message = message;
    }




    public boolean isRegistered() {
        return registered;
    }


    public String getMessage() {
        return message;
    }

}
