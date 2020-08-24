package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.UpdateCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.UpdateElementResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponseStatus;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;
import ru.itmo.core.exception.InvalidCommandException;


import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;

public class UpdateCommand extends Command {

    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;

    public UpdateCommand(MainMultithreading main, ConcurrentSkipListMap<Integer, MusicBand> collection) {
        setMain(main);
        setCollection(collection);
    }

    public static String syntaxDescription =
            "\nCommand: update <id> {element}" +
                    "\nDescription: Updates value of the element with following 'ID'." +
                    "\nNumber of arguments: 2" +
                    "\n   First argument:  id      (Integer)" +
                    "\n   Second argument: element (MusicBand)\n";



    public void execute(UpdateCommandRequest updateCommandRequest, Connection connection) {

        Integer ID = updateCommandRequest.getID();;

        GeneralResponse generalResponse = null;

        try {

            try {
                checkCollectionForEmptiness(collection);
            } catch (InvalidCommandException e) {
                generalResponse = new GeneralResponse(
                        UserCommandResponseStatus.ERROR,
                        e.getMessage());
                throw new StopException();
            }


            if ( ! collection.containsKey(ID)) {
                generalResponse = new GeneralResponse(
                        UserCommandResponseStatus.ERROR,
                        "No element with such 'ID' in the collection.");
                throw new StopException();
            }


            if ( ! DataBaseManager.userOwnsMusicBand(connection, updateCommandRequest.getUser(), ID)) {
                generalResponse = new GeneralResponse(
                        UserCommandResponseStatus.ERROR,
                        String.format(
                                "You can't update element with ID = '%s' as you don't own it.",
                                ID)
                );
                throw new StopException();
            }


            DataBaseManager.updateMusicBandByID(connection, updateCommandRequest.getElement(), ID);
            collection.put(ID, updateCommandRequest.getElement());

            generalResponse = new GeneralResponse(
                    UserCommandResponseStatus.OK,
                    String.format(
                            "Element with ID = '%s' successfully updated.",
                            ID)
            );

        } catch (StopException ignored) {}

        finally {
            if (generalResponse != null) {

                generalResponse.setClient(updateCommandRequest.getClient());

                if ( ! generalResponse.anErrorOccurred()) {
                    main.addMultidirectionalResponse(new UpdateElementResponse(
                            ID,
                            updateCommandRequest.getElement())
                    );
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


    public void setCollection(ConcurrentSkipListMap<Integer, MusicBand> collection) {

        if (collection == null)
            throw new IllegalArgumentException("Invalid collection value : 'null'");

        this.collection = collection;
    }
}
