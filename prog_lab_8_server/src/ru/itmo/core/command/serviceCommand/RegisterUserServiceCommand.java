package ru.itmo.core.command.serviceCommand;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.RegisterUserServiceRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.RegisterUserServiceResponse;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;

import java.sql.Connection;



public class RegisterUserServiceCommand extends ServiceCommand {


    private MainMultithreading main;




    public RegisterUserServiceCommand(MainMultithreading main) {
        this.main = main;
    }




    public void handle(RegisterUserServiceRequest request) {

        Connection connection = main.getConnection();

        Client client = request.getClient();
        User user = request.getUser();

        RegisterUserServiceResponse response = null;

        try {

            int id = DataBaseManager.addUser(connection, user);
            DataBaseManager.addUserToAccessTable(connection, id);

            response = new RegisterUserServiceResponse(
                    client,
                    CRStatus.OK,
                    "Successfully registered.",
                    true
            );

        } catch (DBException e) {
            response = new RegisterUserServiceResponse(
                    client,
                    CRStatus.ERROR,
                    e.getMessage(),
                    false
            );
        } finally {

            main.returnConnection(connection);

            if (response != null) {

                if (response.isRegistered()) {
                    main.addClient(client);
                }

                main.addUnidirectionalResponse(response);
            }
        }
    }
}
