package ru.itmo.core.command.userCommand;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.classes.Person;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.MaxByFrontManCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.exception.InvalidCommandException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.MainMultithreading;

import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;



public class MaxByFrontManCommand extends UserCommand {



    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;

    public MaxByFrontManCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }


    public static String syntaxDescription =
                    "\nCommand: max_by_front_man" +
                    "\n     Description: Prints element, which field 'frontMan' is the greatest." +
                    "\n     Number of arguments: 0\n";



    public void execute(MaxByFrontManCommandRequest request) {

        Connection connection = main.getConnection();

        User user = request.getUser();
        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        try {

            try {
                checkCollectionForEmptiness(collection);
            } catch (InvalidCommandException e) {
                generalResponse = new GeneralResponse(
                        client,
                        CRStatus.ERROR,
                        e.getMessage()
                );
                throw  new StopException();
            }


            String greatestFrontMan
                    = collection.values().stream().max((mb1, mb2) -> Person.compare(mb1.getFrontMan(), mb2.getFrontMan())).toString();

            generalResponse = new GeneralResponse(
                    client,
                    CRStatus.OK,
                    greatestFrontMan
            );

        } catch (StopException ignored) {}

        finally {

            main.returnConnection(connection);

            if (generalResponse != null) {

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
