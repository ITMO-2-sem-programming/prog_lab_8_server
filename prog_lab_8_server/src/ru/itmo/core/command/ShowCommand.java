package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.ShowCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponseStatus;
import ru.itmo.core.main.MainMultithreading;

import java.util.concurrent.ConcurrentSkipListMap;



public class ShowCommand extends Command {


    private final MainMultithreading main;
    private final ConcurrentSkipListMap<Integer, MusicBand> collection;


    public static String syntaxDescription =
                    "\nCommand: show [key]" +
                    "\nDescription: Prints all the elements in collection or the one with specified key." +
                    "\nNumber of arguments: 0 - 1" +
                    "\n   Optional argument: key (Integer)\n";


    public ShowCommand(MainMultithreading main, ConcurrentSkipListMap<Integer, MusicBand> collection) {
        this.main = main;
        this.collection = collection;
    }


    private void execute(ShowCommandRequest showCommandRequest) {
        GeneralResponse generalResponse = new GeneralResponse(
                UserCommandResponseStatus.OK,
                collection.toString());
        generalResponse.setClient(showCommandRequest.getClient());

        main.addUnidirectionalResponse(generalResponse);
    }



}
