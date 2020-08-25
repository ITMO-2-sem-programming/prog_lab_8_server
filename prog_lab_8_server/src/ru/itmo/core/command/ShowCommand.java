package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.ShowCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponseStatus;
import ru.itmo.core.main.MainMultithreading;

import java.util.concurrent.ConcurrentSkipListMap;



public class ShowCommand extends Command {


    private MainMultithreading main;
    private ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection;


    public static String syntaxDescription =
                    "\nCommand: show [key]" +
                    "\nDescription: Prints all the elements in collection or the one with specified key." +
                    "\nNumber of arguments: 0 - 1" +
                    "\n   Optional argument: key (Integer)\n";


    public ShowCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }


    private void execute(ShowCommandRequest request) {
        Client client = request.getClient();;
        GeneralResponse generalResponse = new GeneralResponse(
                client,
                UserCommandResponseStatus.OK,
                collection.toString());

        main.addUnidirectionalResponse(generalResponse);
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
