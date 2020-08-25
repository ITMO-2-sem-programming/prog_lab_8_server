package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.RemoveLowerKeyCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.RemoveElementsResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.RemoveOwnedElementsIDServiceResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponseStatus;
import ru.itmo.core.exception.InvalidCommandException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;



public class RemoveLowerKeyCommand extends Command {


    private MainMultithreading main;
    private ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection;

    public RemoveLowerKeyCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }

    public static String syntaxDescription =
            "Command: remove_lower_key <key>" +
                    "\nDescription: Removes all the elements, which key is less than the specified one." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: key (Integer)\n";





    public void execute(RemoveLowerKeyCommandRequest request) {

        Connection connection = main.getConnection();

        java.lang.Integer ID = request.getID();
        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        ArrayList<Integer> musicBandsIDToRemove = new ArrayList<>();

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

            ArrayList<Integer> ownedMusicBandsByUser = DataBaseManager.getOwnedMusicBandsByUser(connection, user);


            ArrayList<Integer> finalMusicBandsIDToRemove = musicBandsIDToRemove;
            collection
                    .values()
                    .forEach(mb -> {
                        if (mb.getId() < ID
                                & ownedMusicBandsByUser.contains(mb.getId())) {
                            DataBaseManager.removeMusicBand(connection, mb);
                            DataBaseManager.removeOwnedMusicBand(connection, user, mb.getId());
                            collection.remove(mb.getId());

                            musicBandsIDToRemove.add(mb.getId());
                    }
            });

            if ( musicBandsIDToRemove.isEmpty()) {
                generalResponse = new GeneralResponse(
                        client,
                        UserCommandResponseStatus.CANCEL,
                        "No elements were removed."
                );
            } else {
                generalResponse = new GeneralResponse(
                        client,
                        UserCommandResponseStatus.OK,
                        String.format(
                                "Elements with IDs = '%s' were successfully removed.",
                                musicBandsIDToRemove)
                );
            }

        } catch (StopException ignored) {}

        finally {
            if (generalResponse != null) {


                if ( ! generalResponse.isCancelled() ) {
                    main.addMultidirectionalResponse(new RemoveElementsResponse(musicBandsIDToRemove));
                    main.addUnidirectionalResponse(new RemoveOwnedElementsIDServiceResponse(client, musicBandsIDToRemove));
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
