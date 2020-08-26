package ru.itmo.core.common.exchange.response;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.ServiceResponse;

import java.io.Serializable;


public abstract class ServerResponse implements Serializable {


    private Client client;


    public ServerResponse(Client client) {
        setClient(client);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {

//        if (client == null)
//            throw new IllegalArgumentException("Invalid client value : 'null'.");

        this.client = client;

    }



}
