package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;

import java.util.Arrays;
import java.util.List;



public class LoadOwnedElementsIDServiceResponse extends BackgroundServiceResponse {


    List<MusicBand> ownedElements;




    public LoadOwnedElementsIDServiceResponse(Client client, List<MusicBand> ownedElements) {
        super(client);
        setOwnedElements(ownedElements);
    }

    public LoadOwnedElementsIDServiceResponse(Client client, MusicBand... ownedElements) {
        super(client);
        setOwnedElements(Arrays.asList(ownedElements));
    }




    public List<MusicBand> getOwnedElements() {
        return ownedElements;
    }

    private void setOwnedElements(List<MusicBand> ownedElements) {
        if (ownedElements == null)
            throw new IllegalArgumentException("Invalid ownedElements value : 'null'.");
        this.ownedElements = ownedElements;
    }
}
