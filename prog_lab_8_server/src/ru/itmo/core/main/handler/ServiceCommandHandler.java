package ru.itmo.core.main.handler;

import ru.itmo.core.command.serviceCommand.AuthoriseUserServiceCommand;
import ru.itmo.core.command.serviceCommand.ExitServiceCommand;
import ru.itmo.core.command.serviceCommand.LoadOwnedElementsServiceCommand;
import ru.itmo.core.command.serviceCommand.RegisterUserServiceCommand;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.*;
import ru.itmo.core.main.MainMultithreading;


public class ServiceCommandHandler {


    private final AuthoriseUserServiceCommand authoriseUserServiceCommand;
    private final ExitServiceCommand exitServiceCommand;
    private final LoadOwnedElementsServiceCommand loadOwnedElementsServiceCommand;
    private final RegisterUserServiceCommand registerUserServiceCommand;


    public ServiceCommandHandler(MainMultithreading main) {

        authoriseUserServiceCommand = new AuthoriseUserServiceCommand(main);
        exitServiceCommand = new ExitServiceCommand(main);
        loadOwnedElementsServiceCommand = new LoadOwnedElementsServiceCommand(main);
        registerUserServiceCommand = new RegisterUserServiceCommand(main);

    }

    public void handle(ServiceRequest serviceRequest) {

        if (serviceRequest instanceof AuthoriseUserServiceRequest) {
            authoriseUserServiceCommand.handle((AuthoriseUserServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof ExitServiceRequest) {
            exitServiceCommand.handle((ExitServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof  LoadOwnedElementsServiceRequest) {
            loadOwnedElementsServiceCommand.handle((LoadOwnedElementsServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof RegisterUserServiceRequest) {
            registerUserServiceCommand.handle((RegisterUserServiceRequest) serviceRequest);
        }
    }
}
