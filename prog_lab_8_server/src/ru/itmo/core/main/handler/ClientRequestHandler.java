package ru.itmo.core.main.handler;

import ru.itmo.core.common.exchange.request.ClientRequest;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.ServiceRequest;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.UserCommandRequest;
import ru.itmo.core.main.MainMultithreading;


public class ClientRequestHandler {


    ServiceCommandHandler scHandler;
    UserCommandHandler ucHandler;


    public ClientRequestHandler(MainMultithreading main) {

        ucHandler = new UserCommandHandler(main);
        scHandler = new ServiceCommandHandler(main);

    }


    public void handle(ClientRequest clientRequest) {
        System.out.println(1);

        if (clientRequest instanceof UserCommandRequest) {
            ucHandler.handle((UserCommandRequest) clientRequest);
        } else if (clientRequest instanceof ServiceRequest) {
            scHandler.handle((ServiceRequest) clientRequest);
        }
    }



}
