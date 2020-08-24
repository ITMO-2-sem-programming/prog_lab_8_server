package ru.itmo.core.common.exchange.response;

import ru.itmo.core.common.exchange.Client;

import java.io.Serializable;


public abstract class ServerResponse implements Serializable {


    private Client client;




    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {

        if (client == null)
            throw new IllegalArgumentException("Invalid client value : 'null'.");

        this.client = client;
    }



}
