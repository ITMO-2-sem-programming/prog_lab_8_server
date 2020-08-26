package ru.itmo.core.command.userCommand;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.ClearCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.RemoveElementsResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.RemoveOwnedElementsIDServiceResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;



public class ClearCommand extends UserCommand {


    public static String syntaxDescription =
            "\nCommand: clear" +
            "\nDescription: Deletes all the keys and values (clears he collection)." +
            "\nNumber of arguments: 0" +
            "\n";


    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;

    public ClearCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }


    public void execute(ClearCommandRequest request) {


        Connection connection = main.getConnection();

        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        boolean collectionChanged = false;

        ArrayList<Integer> musicBandsIDToRemove = new ArrayList<>();

        try {

            ArrayList<Integer> ownedMusicBandsByUser = DataBaseManager.getOwnedMusicBandsByUser(connection, user);

            collection.values().forEach(mb -> {
                if (ownedMusicBandsByUser.contains(mb.getId())) {
                    DataBaseManager.removeMusicBand(connection, mb);
                    DataBaseManager.removeOwnedMusicBand(connection, user, mb.getId());
                    collection.remove(mb.getId());

                    musicBandsIDToRemove.add(mb.getId());
                }
            });

            if (musicBandsIDToRemove.isEmpty()) {
                generalResponse = new GeneralResponse(
                        client,
                        CRStatus.NEUTRAL,
                        "No elements were removed."
                );
            } else {
                collectionChanged = true;
                generalResponse = new GeneralResponse(
                        client,
                        CRStatus.OK,
                        String.format(
                                "Elements with IDs = '%s' successfully removed.",
                                musicBandsIDToRemove)
                );
            }


        } catch (StopException ignore) {
        } catch (DBException e) {
            generalResponse = new GeneralResponse(
                    client,
                    CRStatus.ERROR,
                    e.getMessage());
        } finally {

            main.returnConnection(connection);

            if (generalResponse != null) {

                if ( collectionChanged ) {
                    main.addMultidirectionalResponse(new RemoveElementsResponse(musicBandsIDToRemove));
                    main.addUnidirectionalResponse(new RemoveOwnedElementsIDServiceResponse(
                            client, 
                            CRStatus.OK,
                            musicBandsIDToRemove));
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
