package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicBand;

public class RemoveByKeyCommandRequest extends UserCommandRequest {


    private Integer ID;



    public RemoveByKeyCommandRequest(Integer ID) {
        setID(ID);
    }




    public Integer getID() {
        return ID;
    }


    private void setID(Integer ID) {

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
