package ru.itmo.core.common.exchange.response.serverResponse.unidirectional.seviceResponse.background;

import java.util.Arrays;
import java.util.List;


public class AddOwnedElementsIDServiceResponse extends BackgroundServiceResponse {


    private List<Integer> ownedElementsID;



    public AddOwnedElementsIDServiceResponse(List<Integer> ownedElementsID) {
        setOwnedElementsID(ownedElementsID);
    }


    public AddOwnedElementsIDServiceResponse(Integer... ownedElementsID) {
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

