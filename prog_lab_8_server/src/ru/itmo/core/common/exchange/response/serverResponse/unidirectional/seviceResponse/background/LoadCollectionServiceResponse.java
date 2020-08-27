package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;

import java.util.List;


public class LoadCollectionServiceResponse extends BackgroundServiceResponse {

    private final List<MusicBand> collection;


    public LoadCollectionServiceResponse(Client client, CRStatus status, String message, List<MusicBand> collection) {
        super(client, status, message);
        this.collection = collection;
    }


    public List<MusicBand> getCollection() {
        return collection;
    }

}
