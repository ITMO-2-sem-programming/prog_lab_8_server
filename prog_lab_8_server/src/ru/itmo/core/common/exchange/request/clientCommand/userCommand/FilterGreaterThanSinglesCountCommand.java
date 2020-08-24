package ru.itmo.core.common.exchange.request.clientCommand.userCommand;


import ru.itmo.core.common.classes.MusicBand;


public class FilterGreaterThanSinglesCountCommand implements UserCommand {


    private Integer singlesCount;


    public FilterGreaterThanSinglesCountCommand(Integer singlesCount) {
        setSinglesCount(singlesCount);
    }




    public Integer getSinglesCount() {
        return singlesCount;
    }


    private void setSinglesCount(Integer singlesCount) {

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
