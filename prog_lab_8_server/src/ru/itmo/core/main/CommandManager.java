package ru.itmo.core.main;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.command.*;
import ru.itmo.core.common.User;
import ru.itmo.core.common.exchange.request.CommandRequest;
import ru.itmo.core.common.exchange.response.CommandResponse;
import ru.itmo.core.exception.DBException;
import ru.itmo.core.exception.InvalidCommandException;

import java.sql.Connection;
import java.util.concurrent.ConcurrentSkipListMap;

public class CommandManager {

    static String dateOfInitialization;


    public static CommandResponse executeCommand(ConcurrentSkipListMap<java.lang.Integer, MusicBand> collection, Connection connection, CommandRequest commandRequest) {

        CommandResponse commandResponse = new CommandResponse();

        User user = commandRequest.getUser();
        String commandName = commandRequest.getCommandName();
        String argumentKey = commandRequest.getArgumentKey();
        MusicBand argumentMusicBand = commandRequest.getArgumentMusicBand();

        commandResponse.setUser(user);

        try {

            switch (commandName) {
                case ("help"):
                    commandResponse.setMessage(HelpCommand.execute(collection, argumentKey));
                    break;


                case ("info"):
                    if (dateOfInitialization == null)
                        commandResponse.setMessage(InfoCommand.execute(collection, "unknown"));
                    else
                        commandResponse.setMessage(InfoCommand.execute(collection, dateOfInitialization));
                    break;


                case ("show"):
                    commandResponse.setMessage(ShowCommand.execute(collection, argumentKey));
                    break;


                case ("insert"):
                    commandResponse.setMessage(InsertCommand.execute(collection, connection, user, argumentMusicBand));
                    break;


                case ("update"):
                    commandResponse.setMessage(UpdateCommand.execute(collection, connection, user, argumentKey, argumentMusicBand));
                    break;


                case ("remove_key"):
                    commandResponse.setMessage(RemoveByKeyCommand.execute(collection, connection, user, argumentKey));
                    break;


                case ("clear"):
                    commandResponse.setMessage(ClearCommand.execute(collection, connection, user));
                    break;


                case ("remove_greater"):
                    commandResponse.setMessage(RemoveGreaterCommand.execute(collection, connection, user, argumentMusicBand));
                    break;


                case ("replace_if_lower"):
                    commandResponse.setMessage(ReplaceIfLowerCommand.execute(collection, connection, user, argumentKey, argumentMusicBand));
                    break;


                case ("remove_lower_key"):
                    commandResponse.setMessage(RemoveLowerKeyCommand.execute(collection, connection, user, argumentKey));
                    break;


                case ("remove_all_by_genre"):
                    commandResponse.setMessage(RemoveAllByGenreCommand.execute(collection, connection, user, argumentKey));
                    break;


                case ("max_by_front_man"):
                    commandResponse.setMessage(MaxByFrontManCommand.execute(collection));
                    break;


                case ("filter_greater_than_singles_count"):
                    commandResponse.setMessage(FilterGreaterThanSinglesCountCommand.execute(collection, java.lang.Integer.parseInt(argumentKey)));
                    break;


                default:
                    commandResponse.setMessage(String.format("Error: Command '%s' isn't supported.", commandName));
                    break;
            }

        } catch (IllegalArgumentException | InvalidCommandException e) {
            commandResponse.setMessage(e.getMessage());
        } catch (DBException e) {
            commandResponse.setMessage(e.getMessage().split("__or__")[0]);
        }

        return commandResponse;
    }








}
