package ru.itmo.core.command.serviceCommand;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.AuthoriseUserServiceRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.AuthorizeUserServiceResponse;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;

import java.sql.Connection;

public class AuthoriseUserServiceCommand extends ServiceCommand {

    private MainMultithreading main;


    public AuthoriseUserServiceCommand(MainMultithreading main) {
        this.main = main;
    }


    public void handle(AuthoriseUserServiceRequest request) {

        Connection connection = main.getConnection();

        Client client = request.getClient();
        User user = request.getUser();

        AuthorizeUserServiceResponse response = null;

        User userFromDB;

        try {
            userFromDB = DataBaseManager.getUserByUserName(connection, user.getLogin());

            if (userFromDB == null) {
                response = new AuthorizeUserServiceResponse(
                        client,
                        CRStatus.ERROR,
                        String.format(
                                "No user with the user name = '%s'.",
                                user.getLogin()),
                        false
                );

            } else if ( ! user.getPassword().equals(userFromDB.getPassword())) {
                response = new AuthorizeUserServiceResponse(
                        client,
                        CRStatus.ERROR,
                        "Wrong password.",
                        false
                );

            } else {
                response = new AuthorizeUserServiceResponse(
                        client,
                        CRStatus.OK,
                        "Authorized successfully.",
                        true
                );
            }

        } catch (DBException e) {
            response = new AuthorizeUserServiceResponse(
                    client,
                    CRStatus.ERROR,
                    e.getMessage(),
                    false
            );
        } finally {

            main.returnConnection(connection);

            if (response != null) {

                if (response.isAuthorized()) {
                    main.addClient(client);
                }

                main.addUnidirectionalResponse(response);
            }
        }

    }
}
