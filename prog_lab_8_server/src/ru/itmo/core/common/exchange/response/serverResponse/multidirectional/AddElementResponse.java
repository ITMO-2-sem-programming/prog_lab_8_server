package ru.itmo.core.common.exchange.response.serverResponse.multidirectional;


import ru.itmo.core.common.classes.MusicBand;


public class AddElementResponse extends MultidirectionalResponse {


    private MusicBand element;



    public AddElementResponse(MusicBand element) {
        setElement(element);
    }




    public MusicBand getElement() {
        return element;
    }


    private void setElement(MusicBand element) {

        if (element == null)
            throw new IllegalArgumentException(
                    "Invalid element value : 'null'."
            );

        this.element = element;

    }



}
