package ru.itmo.core.common.exchange.request.clientCommand.userCommand;


import ru.itmo.core.common.classes.MusicBand;


public class RemoveLowerKeyCommand implements UserCommand {


    public RemoveLowerKeyCommand(Integer ID) {
        setID(ID);
    }


    private Integer ID;




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
