package ru.itmo.core.common.exchange.response.serverResponse.multidirectional;


import ru.itmo.core.common.classes.MusicBand;

public class UpdateElementResponse extends MultidirectionalResponse {


    private Integer elementID;
    private MusicBand element;



    public UpdateElementResponse(Integer elementID, MusicBand element) {
        this.elementID = elementID;
        this.element = element;
    }




    public Integer getElementID() {
        return elementID;
    }


    private void setElementID(Integer elementID) {

        if (elementID == null || elementID <= 0)
            throw new IllegalArgumentException(String.format(

                    "Illegal element ID : '%s'",

                    elementID)
            );

        this.elementID = elementID;

    }


    public MusicBand getElement() {
        return element;

    }


    private void setElement(MusicBand element) {

        if (element == null)
            throw new IllegalArgumentException(
                    "Element can't be null."
            );

        this.element = element;

    }



}
