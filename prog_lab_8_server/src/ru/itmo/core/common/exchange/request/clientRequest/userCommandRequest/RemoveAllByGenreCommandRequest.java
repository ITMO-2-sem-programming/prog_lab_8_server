package ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest;


import ru.itmo.core.common.classes.MusicGenre;


public class RemoveAllByGenreCommandRequest extends UserCommandRequest {


    MusicGenre genre;



    public RemoveAllByGenreCommandRequest(MusicGenre genre) {
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
