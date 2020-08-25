package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicBand;


public class RemoveLowerKeyCommandRequest extends UserCommandRequest {


    public RemoveLowerKeyCommandRequest(java.lang.Integer ID) {
        setID(ID);
    }


    private java.lang.Integer ID;




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



}
