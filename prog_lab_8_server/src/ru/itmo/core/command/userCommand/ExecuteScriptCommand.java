package ru.itmo.core.command.userCommand;


public class ExecuteScriptCommand extends UserCommand {

    public static String syntaxDescription =
                    "\nCommand: execute_script <file_path>" +
                    "\nDescription: Executes the commands from the file." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument:  file_path (String)\n";

}
