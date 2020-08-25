package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.FilterGreaterThanSinglesCountCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UCStatus;
import ru.itmo.core.exception.InvalidCommandException;
import ru.itmo.core.exception.StopException;
import ru.itmo.core.main.MainMultithreading;

import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;



public class FilterGreaterThanSinglesCountCommand extends Command {


    public static String syntaxDescription =
            "\nCommand: filter_greater_than_singles_count <singlesCount>" +
                    "\nDescription: Prints the elements, which 'singlesCount' value is greater than the specified." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument:  singlesCount (int)\n";


    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;




    public FilterGreaterThanSinglesCountCommand(MainMultithreading main) {
        setMain(main);
        setCollection(main.getCollection());
    }


    public void execute(FilterGreaterThanSinglesCountCommandRequest request) {

        Integer singlesCount = request.getSinglesCount();

        Client client = request.getClient();

        GeneralResponse generalResponse = null;

        String filteredMusicBands;

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

            filteredMusicBands = collection.values().stream().filter(x -> x.getSinglesCount() > singlesCount).map(MusicBand::toString).collect(Collectors.joining());

            generalResponse = new GeneralResponse(
                    client,
                    UCStatus.OK,
                    filteredMusicBands
            );
        } catch (StopException ignore) {}

        finally {
            main.addUnidirectionalResponse(generalResponse);
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
