package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicBand;


public class ReplaceIfLowerCommandRequest extends UserCommandRequest {


    private java.lang.Integer ID;
    private MusicBand element;



    public ReplaceIfLowerCommandRequest(java.lang.Integer ID, MusicBand element) {
        setID(ID);
        setElement(element);
    }




    public java.lang.Integer getID() {
        return ID;
    }


    private void setID(java.lang.Integer ID) {

        if ( ! (ID >= 1))
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid id : '%s'"
                                    + "\n" + MusicBand.musicBandFieldsDescription.get("id"),
                            ID
                    )
            );

        this.ID = ID;

    }


    public MusicBand getElement() {
        return element;
    }


    private void setElement(MusicBand element) {

        if (element == null)
            throw new IllegalArgumentException("Element can't be null.");

        this.element = element;
    }



}
