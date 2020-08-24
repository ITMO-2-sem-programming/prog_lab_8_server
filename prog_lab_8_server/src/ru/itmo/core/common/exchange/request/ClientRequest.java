package ru.itmo.core.common.exchange.request;


import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;

import java.io.Serializable;


public abstract class ClientRequest implements Serializable {


    private Client client;
    private User user;



    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {

        if (client == null)
            throw new IllegalArgumentException("Invalid client value : 'null'.");

        this.client = client;
    }


    public User getUser() {
        return user;
    }


    public void setUser(User user) {

        if (user == null)
            throw new IllegalArgumentException("Invalid user value : 'null'.");

        this.user = user;
    }



}
