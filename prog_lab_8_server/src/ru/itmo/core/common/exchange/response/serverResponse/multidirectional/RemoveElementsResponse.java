package ru.itmo.core.common.exchange.response.serverResponse.multidirectional;


import ru.itmo.core.common.classes.MusicBand;

import java.util.Arrays;
import java.util.List;

public class RemoveElementsResponse extends MultidirectionalResponse {


    private List<Integer> elementsID;



    public RemoveElementsResponse(Integer... elementsID) {
        setElementsID(Arrays.asList(elementsID.clone()));
    }


    public RemoveElementsResponse(List<Integer> elementsID) {
        setElementsID(elementsID);
    }




    public List<Integer> getElementsID() {
        return elementsID;
    }


    private void setElementsID(List<Integer> elementsID) {

        if (elementsID == null)
            throw new IllegalArgumentException(
                    "Invalid element value : 'null'."
            );

        this.elementsID = elementsID;

    }



}
