package ru.itmo.core.command;

import ru.itmo.core.common.exchange.Client;
import ru.itmo.core.common.exchange.request.clientRequest.userCommandRequest.HelpCommandRequest;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.GeneralResponse;
import ru.itmo.core.common.exchange.response.serverResponse.unidirectional.userResponse.UserCommandResponseStatus;
import ru.itmo.core.main.MainMultithreading;



public class HelpCommand extends Command {


    public static String syntaxDescription =
            "\nCommand: help [command_name]" +
                    "\nDescription: Prints the information about all of the command or the specified one." +
                    "\nNumber of arguments: 0 or 1" +
                    "\n   Optional argument:  command_name (String)\n";


    private MainMultithreading main;




    public HelpCommand(MainMultithreading main) {
        setMain(main);
    }




    private void execute(HelpCommandRequest request) {

        Client client = request.getClient();

        String help =  HelpCommand.syntaxDescription +
                InfoCommand.syntaxDescription +
                ShowCommand.syntaxDescription +
                InsertCommand.syntaxDescription +
                UpdateCommand.syntaxDescription +
                RemoveByKeyCommand.syntaxDescription +
                ClearCommand.syntaxDescription +
                SaveCommand.syntaxDescription +
                ExecuteScriptCommand.syntaxDescription +
                ExitCommand.syntaxDescription +
                RemoveGreaterCommand.syntaxDescription +
                ReplaceIfLowerCommand.syntaxDescription +
                RemoveLowerKeyCommand.syntaxDescription +
                RemoveAllByGenreCommand.syntaxDescription +
                MaxByFrontManCommand.syntaxDescription +
                FilterGreaterThanSinglesCountCommand.syntaxDescription;


        GeneralResponse generalResponse = new GeneralResponse(
                client,
                UserCommandResponseStatus.CANCEL,
                help
        );

        main.addUnidirectionalResponse(generalResponse);

    }


    public void setMain(MainMultithreading main) {

        if (main == null)
            throw new IllegalArgumentException("Invalid main value : 'null'.");

        this.main = main;
    }
}
