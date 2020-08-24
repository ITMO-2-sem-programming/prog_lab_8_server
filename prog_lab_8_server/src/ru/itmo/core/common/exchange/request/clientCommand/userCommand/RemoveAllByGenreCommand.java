package ru.itmo.core.common.exchange.request.clientCommand.userCommand;


import ru.itmo.core.common.classes.MusicGenre;


public class RemoveAllByGenreCommand implements UserCommand {


    MusicGenre genre;



    public RemoveAllByGenreCommand(MusicGenre genre) {
        setGenre(genre);
    }




    public MusicGenre getGenre() {
        return genre;
    }

    private void setGenre(MusicGenre genre) {

        if (genre == null)
            throw new IllegalArgumentException("Genre can't be null.");
        this.genre = genre;
    }



}
