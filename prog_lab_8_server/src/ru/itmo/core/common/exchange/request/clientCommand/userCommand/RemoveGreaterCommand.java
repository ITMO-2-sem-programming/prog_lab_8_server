package ru.itmo.core.common.exchange.request.clientCommand.userCommand;


import ru.itmo.core.common.classes.MusicBand;


public class RemoveGreaterCommand implements UserCommand {


    private MusicBand element;



    public RemoveGreaterCommand(MusicBand element) {
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
