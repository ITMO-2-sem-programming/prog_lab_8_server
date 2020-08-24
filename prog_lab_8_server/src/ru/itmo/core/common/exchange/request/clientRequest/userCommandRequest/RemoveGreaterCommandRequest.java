package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicBand;


public class RemoveGreaterCommandRequest extends UserCommandRequest {


    private MusicBand element;



    public RemoveGreaterCommandRequest(MusicBand element) {
        setElement(element);
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
