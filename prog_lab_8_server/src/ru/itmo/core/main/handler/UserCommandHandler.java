package ru.itmo.core.main.handler;


import ru.itmo.core.command.UpdateCommand;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.UpdateCommandRequest;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.UserCommandRequest;


import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;



public class UserCommandHandler {


    private ConcurrentSkipListMap<Integer, MusicBand> collection;


    public UserCommandHandler(ConcurrentSkipListMap<Integer, MusicBand> collection) {
        this.collection = collection;
    }


    public void handle(User user, Connection connection, UserCommandRequest userCommand) {

        if (userCommand instanceof UpdateCommandRequest) {
            UpdateCommandRequest updateCommandRequest = (UpdateCommandRequest) userCommand;
            UpdateCommand.execute(
                    collection,
                    connection,
                    user,
                    updateCommandRequest.getID(),
                    updateCommandRequest.getElement()
            );
        }
    }
}
