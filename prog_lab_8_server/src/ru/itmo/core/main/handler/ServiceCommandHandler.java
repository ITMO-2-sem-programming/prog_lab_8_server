package ru.itmo.core.main.handler;

import ru.itmo.core.command.serviceCommand.*;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.*;
import ru.itmo.core.main.MainMultithreading;


public class ServiceCommandHandler {


    private final LoadCollectionServiceCommand loadCollectionServiceCommand;
    private final AuthoriseUserServiceCommand authoriseUserServiceCommand;
    private final ExitServiceCommand exitServiceCommand;
    private final LoadOwnedElementsIDServiceCommand loadOwnedElementsServiceCommand;
    private final RegisterUserServiceCommand registerUserServiceCommand;


    public ServiceCommandHandler(MainMultithreading main) {

        loadCollectionServiceCommand = new LoadCollectionServiceCommand(main);
        authoriseUserServiceCommand = new AuthoriseUserServiceCommand(main);
        exitServiceCommand = new ExitServiceCommand(main);
        loadOwnedElementsServiceCommand = new LoadOwnedElementsIDServiceCommand(main);
        registerUserServiceCommand = new RegisterUserServiceCommand(main);

    }

    public void handle(ServiceRequest serviceRequest) {

        if (serviceRequest instanceof LoadCollectionServiceRequest) {
            loadCollectionServiceCommand.handle((LoadCollectionServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof AuthoriseUserServiceRequest) {
            authoriseUserServiceCommand.handle((AuthoriseUserServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof ExitServiceRequest) {
            exitServiceCommand.handle((ExitServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof  LoadOwnedElementsServiceRequest) {
            loadOwnedElementsServiceCommand.handle((LoadOwnedElementsServiceRequest) serviceRequest);
        } else if (serviceRequest instanceof RegisterUserServiceRequest) {
            registerUserServiceCommand.handle((RegisterUserServiceRequest) serviceRequest);
        } else throw new IllegalArgumentException("Unknown serviceRequest");
    }
}
