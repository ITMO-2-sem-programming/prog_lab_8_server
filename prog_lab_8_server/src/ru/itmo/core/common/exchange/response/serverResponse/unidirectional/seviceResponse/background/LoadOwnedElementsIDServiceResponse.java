package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.CRStatus;

import java.util.Arrays;
import java.util.List;



public class LoadOwnedElementsIDServiceResponse extends BackgroundServiceResponse {


    List<Integer> ownedElementsID;




    public LoadOwnedElementsIDServiceResponse(Client client, CRStatus status, String message, List<Integer> ownedElementsID) {
        super(client, status, message);
        setOwnedElementsID(ownedElementsID);
    }


    public LoadOwnedElementsIDServiceResponse(Client client, CRStatus status, List<Integer> ownedElementsID) {
        super(client, status, null);
        setOwnedElementsID(ownedElementsID);
    }


    public LoadOwnedElementsIDServiceResponse(Client client, CRStatus status, String message, Integer... ownedElementsID) {
        super(client, status, message);
        setOwnedElementsID(Arrays.asList(ownedElementsID));
    }


    public LoadOwnedElementsIDServiceResponse(Client client, CRStatus status, Integer... ownedElementsID) {
        super(client, status, null);
        setOwnedElementsID(Arrays.asList(ownedElementsID));
    }




    public List<Integer> getOwnedElementsID() {
        return ownedElementsID;
    }

    private void setOwnedElementsID(List<Integer> ownedElementsID) {
        if (ownedElementsID == null)
            throw new IllegalArgumentException("Invalid ownedElementsID value : 'null'.");
        this.ownedElementsID = ownedElementsID;
    }
}
