package ru.itmo.core.main.handler;


import ru.itmo.core.command.userCommand.*;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.*;
import ru.itmo.core.main.MainMultithreading;


public class UserCommandHandler {



    private ClearCommand clearCommand;
//    ExecuteScriptCommand executeScriptCommand;//
    private ExitCommand exitCommand; // TODO: 25.08.2020
    private FilterGreaterThanSinglesCountCommand filterGreaterThanSinglesCountCommand;
    private HelpCommand helpCommand;
    private InfoCommand infoCommand;
    private InsertCommand insertCommand;
    private MaxByFrontManCommand maxByFrontManCommand;
    private RemoveAllByGenreCommand removeAllByGenreCommand;
    private RemoveByKeyCommand removeByKeyCommand;

    private RemoveGreaterCommand removeGreaterCommand;
    private RemoveLowerKeyCommand removeLowerKeyCommand;
    private ReplaceIfLowerCommand replaceIfLowerCommand;
//    SaveCommand saveCommand;
    private ShowCommand showCommand;
    private UpdateCommand updateCommand;



    public UserCommandHandler(MainMultithreading main) {

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
        } else if (userCommandRequest instanceof InsertCommandRequest) {
            insertCommand.execute((InsertCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof MaxByFrontManCommandRequest) {
            maxByFrontManCommand.execute((MaxByFrontManCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof RemoveAllByGenreCommandRequest) {
            removeAllByGenreCommand.execute((RemoveAllByGenreCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof RemoveByKeyCommandRequest) {
            removeByKeyCommand.execute((RemoveByKeyCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof RemoveGreaterCommandRequest) {
            removeGreaterCommand.execute((RemoveGreaterCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof RemoveLowerKeyCommandRequest) {
            removeLowerKeyCommand.execute((RemoveLowerKeyCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof ReplaceIfLowerCommandRequest) {
            replaceIfLowerCommand.execute((ReplaceIfLowerCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof ShowCommandRequest) {
            showCommand.execute((ShowCommandRequest) userCommandRequest);
        } else if (userCommandRequest instanceof UpdateCommandRequest) {
            updateCommand.execute((UpdateCommandRequest) userCommandRequest);
        }
    }
}
