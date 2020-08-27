package ru.itmo.core.command.serviceCommand;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.request.clientRequest.serviceRequest.LoadCollectionServiceRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background.LoadCollectionServiceResponse;
import ru.itmo.core.main.MainMultithreading;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentSkipListMap;

public class LoadCollectionServiceCommand extends ServiceCommand {

    private MainMultithreading main;
    private ConcurrentSkipListMap<Integer, MusicBand> collection;


    public LoadCollectionServiceCommand(MainMultithreading main) {
        this.main = main;
        this.collection = main.getCollection();

    }


    public void handle(LoadCollectionServiceRequest request) {

        Client client = request.getClient();

        LoadCollectionServiceResponse response = new LoadCollectionServiceResponse(
                client,
                CRStatus.OK,
                "Collection loaded successfully",
                new ArrayList<>(collection.values())
        );

        main.addUnidirectionalResponse(response);
    }


}
