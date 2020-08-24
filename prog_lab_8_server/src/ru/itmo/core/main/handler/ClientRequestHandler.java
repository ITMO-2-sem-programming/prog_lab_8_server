package ru.itmo.core.main.handler;

import ru.itmo.core.common.exchange.request.ClientRequest;
import ru.itmo.core.common.exchange.request.Request;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.ServiceRequest;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.UserCommandRequest;
import ru.itmo.core.main.MainMultithreading;


public class ClientRequestHandler {


    MainMultithreading main;

    public void handle(Request request) {

        if (request.getClientRequest() instanceof UserCommandRequest) {

        } else if (request.getClientRequest() instanceof ServiceRequest) {

        }

    }
}
