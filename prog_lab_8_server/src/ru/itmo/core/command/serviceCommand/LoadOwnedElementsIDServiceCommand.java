package ru.itmo.core.command.serviceCommand;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadOwnedElementsServiceRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.LoadOwnedElementsIDServiceResponse;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;

import java.sql.Connection;
import java.util.List;

public class LoadOwnedElementsIDServiceCommand extends ServiceCommand {

    private MainMultithreading main;

    public LoadOwnedElementsIDServiceCommand(MainMultithreading main) {
        this.main = main;
    }


    public void handle(LoadOwnedElementsServiceRequest request) {

        Connection connection = main.getConnection();

        Client client = request.getClient();
        User user = request.getUser();

        LoadOwnedElementsIDServiceResponse response = null;

        try {

            List<Integer> ownedMusicBandsIDByUser = DataBaseManager.getOwnedMusicBandsByUser(connection, user);

            response = new LoadOwnedElementsIDServiceResponse(
                    client,
                    CRStatus.OK,
                    "Owned elements ID loaded successfully.",
                    ownedMusicBandsIDByUser
            );
        } catch (DBException e) {
            response = new LoadOwnedElementsIDServiceResponse(
                    client,
                    CRStatus.ERROR,
                    e.getMessage()
            );
        } finally {
            
            main.returnConnection(connection);
            
            if (response != null) {
                main.addUnidirectionalResponse(response);
            }
        }
    }
}
