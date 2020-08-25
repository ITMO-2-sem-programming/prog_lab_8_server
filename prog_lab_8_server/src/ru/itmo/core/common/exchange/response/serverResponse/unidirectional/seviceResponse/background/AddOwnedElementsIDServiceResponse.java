package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background;

import ru.itmo.core.common.exchange.Client;

import java.util.Arrays;
import java.util.List;


public class AddOwnedElementsIDServiceResponse extends BackgroundServiceResponse {


    private List<Integer> ownedElementsID;



    public AddOwnedElementsIDServiceResponse(Client client, List<Integer> ownedElementsID) {
        super(client);
        setOwnedElementsID(ownedElementsID);
    }


    public AddOwnedElementsIDServiceResponse(Client client, Integer... ownedElementsID) {
        super(client);
        setOwnedElementsID(Arrays.asList(ownedElementsID));
    }




    public List<Integer> getOwnedElementsID() {
        return ownedElementsID;
    }


    public void setOwnedElementsID(List<Integer> ownedElementsID) {

        for (Integer id : ownedElementsID) {
            if (id == null || id <= 0)
                throw new IllegalArgumentException(String.format(
                        "Invalid ID value : '%s'",
                        id)
                );
        }

        this.ownedElementsID = ownedElementsID;

    }



}

