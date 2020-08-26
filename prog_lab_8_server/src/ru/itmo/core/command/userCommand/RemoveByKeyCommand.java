package ru.itmo.core.command.userCommand;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.RemoveByKeyCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.RemoveElementsResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.RemoveOwnedElementsIDServiceResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.exception.InvalidCommandException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;


import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;



public class RemoveByKeyCommand extends UserCommand {


    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;


    public RemoveByKeyCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }

    public static String syntaxDescription =
            "\nCommand: remove_key <key>" +
                    "\nDescription: Removes an element with the specified key." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: key (Integer)\n";


    public void execute(RemoveByKeyCommandRequest request) {

        Connection connection = main.getConnection();

        Integer ID = request.getID();
        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        boolean collectionChanged = false;

        try {

            try {
                checkCollectionForEmptiness(collection);
            } catch (InvalidCommandException e) {
                generalResponse = new GeneralResponse(
                        client,
                        CRStatus.ERROR,
                        e.getMessage());
                throw new StopException();
            }

            if ( ! collection.containsKey(ID)) {
                generalResponse = new GeneralResponse(
                        client,
                        CRStatus.ERROR,
                        String.format(
                            "No element with ID = '%s' in the collection.",
                                ID)
                );
                throw new StopException();
            }

            if ( ! DataBaseManager.userOwnsMusicBand(connection, user, ID)) {
                generalResponse = new GeneralResponse(
                        client,
                        CRStatus.ERROR,
                        String.format(
                            "You can't remove element with ID = '%s' as you don't own it.",
                        ID)
                );
                throw new StopException();

            }

            DataBaseManager.removeMusicBandByID(connection, ID);
            DataBaseManager.removeOwnedMusicBand(connection, user, ID);
            collection.remove(ID);

            collectionChanged = true;

            generalResponse = new GeneralResponse(
                    client,
                    CRStatus.OK,
                    String.format(
                            "Element with ID = '%s' successfully removed.",
                            ID)
            );

        } catch (StopException ignored) {
        } catch (DBException e) {
            generalResponse = new GeneralResponse(
                    client,
                    CRStatus.ERROR,
                    e.getMessage());
        } finally {

            main.returnConnection(connection);

            if (generalResponse != null) {

                if ( collectionChanged ) {
                    main.addMultidirectionalResponse(new RemoveElementsResponse(ID));
                    main.addUnidirectionalResponse(new RemoveOwnedElementsIDServiceResponse(
                            client,
                            CRStatus.OK,
                            ID));
                }

                main.addUnidirectionalResponse(generalResponse);


            }

        }
    }


    public void setMain(MainMultithreading main) {

        if (main == null)
            throw new IllegalArgumentException("Invalid main value : 'null'.");

        this.main = main;
    }


    public void setCollection(ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection) {

        if (collection == null)
            throw new IllegalArgumentException("Invalid collection value : 'null'");

        this.collection = collection;
    }

}