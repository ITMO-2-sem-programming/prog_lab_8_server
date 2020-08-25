package ru.itmo.core.main.handler;


import ru.itmo.core.command.*;
import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.common.exchange.User;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.*;
import ru.itmo.core.main.MainMultithreading;


import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;



public class UserCommandHandler {

    MainMultithreading main;


    ClearCommand clearCommand;
//    ExecuteScriptCommand executeScriptCommand;//
    ExitCommand exitCommand; // TODO: 25.08.2020
    FilterGreaterThanSinglesCountCommand filterGreaterThanSinglesCountCommand;
    HelpCommand helpCommand;
    InfoCommand infoCommand;
    InsertCommand insertCommand;
    MaxByFrontManCommand maxByFrontManCommand;
    RemoveAllByGenreCommand removeAllByGenreCommand;
    RemoveByKeyCommand removeByKeyCommand;

    RemoveGreaterCommand removeGreaterCommand;
    RemoveLowerKeyCommand removeLowerKeyCommand;
    ReplaceIfLowerCommand replaceIfLowerCommand;
//    SaveCommand saveCommand;
    ShowCommand showCommand;
    UpdateCommand updateCommand;



    public UserCommandHandler(MainMultithreading main) {
        this.main = main;

        clearCommand = new ClearCommand(main);
        filterGreaterThanSinglesCountCommand = new FilterGreaterThanSinglesCountCommand(main);
        helpCommand = new HelpCommand(main);
        infoCommand = new InfoCommand(main);
        insertCommand = new InsertCommand(main);
        maxByFrontManCommand = new MaxByFrontManCommand(main);
        removeAllByGenreCommand = new RemoveAllByGenreCommand(main);
        removeByKeyCommand = new RemoveByKeyCommand(main);
        removeGreaterCommand = new RemoveGreaterCommand(main);
        removeLowerKeyCommand = new RemoveLowerKeyCommand(main);
        replaceIfLowerCommand = new ReplaceIfLowerCommand(main);
        showCommand = new ShowCommand(main);
        updateCommand = new UpdateCommand(main);


    }


    public void handle(UserCommandRequest userCommandRequest) {

        if (userCommandRequest instanceof ClearCommandRequest) {
            clearCommand.execute((ClearCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof FilterGreaterThanSinglesCountCommandRequest) {
            filterGreaterThanSinglesCountCommand.execute((FilterGreaterThanSinglesCountCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof HelpCommandRequest) {
            helpCommand.execute((HelpCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof InfoCommandRequest) {
            infoCommand.execute((InfoCommandRequest) userCommandRequest);
        } else if // TODO: 25.08.2020  
    }
}
