package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicBand;


public class UpdateCommandRequest extends UserCommandRequest {


    private Integer ID;
    private MusicBand element;



    public UpdateCommandRequest(Integer ID, MusicBand element) {
        setID(ID);
        setElement(element);
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


    public MusicBand getElement() {
        return element;
    }


    private void setElement(MusicBand element) {

        if (element == null)
            throw new IllegalArgumentException("Element can't be null.");

        this.element = element;
    }



}




