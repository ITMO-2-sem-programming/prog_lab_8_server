package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicBand;


public class FilterGreaterThanSinglesCountCommandRequest extends UserCommandRequest {


    private java.lang.Integer singlesCount;


    public FilterGreaterThanSinglesCountCommandRequest(java.lang.Integer singlesCount) {
        setSinglesCount(singlesCount);
    }




    public java.lang.Integer getSinglesCount() {
        return singlesCount;
    }


    private void setSinglesCount(java.lang.Integer singlesCount) {

        if ( ! (singlesCount >= 1))
            throw new IllegalArgumentException(
                    String.format(
                            "Invalid id : '%s'"
                                    + "\n" + MusicBand.musicBandFieldsDescription.get("singlesCount"),
                            singlesCount
                    )
            );

        this.singlesCount = singlesCount;

    }



}
