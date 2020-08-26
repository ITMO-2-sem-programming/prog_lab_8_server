package ru.itmo.core.command.serviceCommand;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.ExitServiceRequest;
import ru.itmo.core.main.MainMultithreading;



public class ExitServiceCommand extends ServiceCommand {


    private MainMultithreading main;



    public ExitServiceCommand(MainMultithreading main) {
        this.main = main;
    }




    public void handle(ExitServiceRequest request) {

        Client client = request.getClient();

        main.removeClient(client);
    }



}
