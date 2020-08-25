package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.InsertCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.multidirectional.AddElementResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.AddOwnedElementsIDServiceResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UCStatus;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.DataBaseManager;
import ru.itmo.core.main.MainMultithreading;


import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;



public class InsertCommand extends Command {



    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;

    public InsertCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }

    public static String syntaxDescription =
            "\nCommand: insert <key> {element}" +
                    "\nDescription: Adds new element with the following key (if the key doesn't exist)." +
                    "\nNumber of arguments: 2" +
                    "\n   First argument:  key     (Integer)" +
                    "\n   Second argument: element (MusicBand)\n";



    public void execute(InsertCommandRequest request) {

        Connection connection = main.getConnection();

        MusicBand element = request.getElement();
        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        boolean collectionChanged = false;
        
        Integer ID = null;

        try {
            
            ID = DataBaseManager.addMusicBand(connection, element);
            DataBaseManager.addOwnedMusicBandIDByUserID(connection, DataBaseManager.getUserIDByUserName(connection, user.getLogin()), ID);

            element.setId(ID);
            collection.put(ID, element);

            collectionChanged = true;

            generalResponse = new GeneralResponse(
                    client,
                    UCStatus.OK,
                    String.format(
                            "Element with ID = '%s' successfully added to collection.",
                            ID)
            );


        } catch (StopException ignore) {
        } catch (DBException e) {
            generalResponse = new GeneralResponse(
                    client,
                    UCStatus.ERROR,
                    e.getMessage());
        } finally {

            main.returnConnection(connection);

            if (generalResponse != null) {

                if ( collectionChanged ) {
                    main.addMultidirectionalResponse(new AddElementResponse(element));
                    main.addUnidirectionalResponse(new AddOwnedElementsIDServiceResponse(client, ID));
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