package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.RemoveGreaterCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.RemoveElementsResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.RemoveOwnedElementsIDServiceResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UCStatus;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.exception.InvalidCommandException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;



public class RemoveGreaterCommand extends Command {


    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;

    public RemoveGreaterCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }


    public static String syntaxDescription =
                    "\nCommand: remove_greater {element}" +
                    "\nDescription: Removes all the element greater than the specified one." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: element (MusicBand)\n";


    public  void execute(RemoveGreaterCommandRequest request) {

        Connection connection = main.getConnection();

        MusicBand element = request.getElement();
        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        boolean collectionChanged = false;

        ArrayList<Integer> musicBandsIDToRemove = new ArrayList<>();


        try {

            try {
                checkCollectionForEmptiness(collection);
            } catch (InvalidCommandException e) {
                generalResponse = new GeneralResponse(
                        client,
                        UCStatus.ERROR,
                        e.getMessage()
                );
                throw  new StopException();
            }

            ArrayList<Integer> ownedMusicBandsByUser = DataBaseManager.getOwnedMusicBandsByUser(connection, user);

            collection.values().forEach(mb -> {
                if (
                        mb.isMoreThan(element)
                        & ownedMusicBandsByUser.contains(mb.getId())) {
                    DataBaseManager.removeMusicBand(connection, mb);
                    DataBaseManager.removeOwnedMusicBand(connection, user, mb.getId());
                    collection.remove(mb.getId());

                    musicBandsIDToRemove.add(mb.getId());

                }
            });

            if ( musicBandsIDToRemove.isEmpty() ) {
                generalResponse = new GeneralResponse(
                        client,
                        UCStatus.NEUTRAL,
                        "No elements were removed."
                );

            } else {
                collectionChanged = true;
                generalResponse = new GeneralResponse(
                        client,
                        UCStatus.OK,
                        String.format(
                                "Elements with IDs = '%s' successfully removed.",
                                musicBandsIDToRemove)
                );
            }


        } catch (StopException ignored) {
        } catch (DBException e) {
            generalResponse = new GeneralResponse(
                    client,
                    UCStatus.ERROR,
                    e.getMessage());
        } finally {

            main.returnConnection(connection);

            if (generalResponse != null) {

                if ( collectionChanged ) {
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
