package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.ReplaceIfLowerCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.UpdateElementResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponseStatus;
import ru.itmo.core.exception.InvalidCommandException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;


import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;


public class ReplaceIfLowerCommand extends Command {


    private MainMultithreading main;
    private ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection;


    public static String syntaxDescription =
            "\nCommand: replace_if_lower <key> {element}" +
                    "\nDescription: Replaces the element of specified key with the specified element if the new one is less." +
                    "\nNumber of arguments 2: " +
                    "\n   First argument:  key     (Integer)" +
                    "\n   Second argument: element (MusicBand)\n";


    public ReplaceIfLowerCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }


    public void execute(ReplaceIfLowerCommandRequest request) {

        Connection connection = main.getConnection();

        java.lang.Integer ID = request.getID();
        MusicBand element = request.getElement();
        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        try {

            try {
                checkCollectionForEmptiness(collection);
            } catch (InvalidCommandException e) {
                generalResponse = new GeneralResponse(
                        client,
                        UserCommandResponseStatus.CANCEL,
                        e.getMessage());
                throw new StopException();
            }

            if ( ! collection.containsKey(ID)) {
                generalResponse = new GeneralResponse(
                        client,
                        UserCommandResponseStatus.CANCEL,
                        String.format(
                                "No element with ID = '%s' in the collection.",
                                ID)
                );
                throw new StopException();
            }

            if ( ! DataBaseManager.userOwnsMusicBand(connection, user, element)) {
                generalResponse = new GeneralResponse(
                        client,
                        UserCommandResponseStatus.CANCEL,
                        String.format(
                                "You can't replace element with ID = '%s' as you don't own it.",
                                ID)
                );
                throw new StopException();

            }

            if ( ! request.getElement().isLessThan(collection.get(ID))) {


                generalResponse = new GeneralResponse(
                        client,
                        UserCommandResponseStatus.CANCEL,
                        String.format(
                                "Element wasn't replaced as the new one is not lower than the element with ID = '%s'.",
                                ID)
                );
                throw new StopException();

            }


            DataBaseManager.updateMusicBandByID(connection, element, ID);
            collection.put(ID, element);


            generalResponse = new GeneralResponse(
                    client,
                    UserCommandResponseStatus.OK,
                    String.format(
                            "Element with ID = '%s' was successfully replaced.",
                            ID)
            );

        } catch (StopException ignored) {

        } finally {

            main.returnConnection(connection);

            if (generalResponse != null) {


                if ( ! generalResponse.isCancelled() ) {
                    main.addMultidirectionalResponse(new UpdateElementResponse(ID, element));
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
