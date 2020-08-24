package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.main.DataBaseManager;
import ru.itmo.core.common.User;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;


public class RemoveGreaterCommand extends Command {

    public static String syntaxDescription =
                    "\nCommand: remove_greater {element}" +
                    "\nDescription: Removes all the element greater than the specified one." +
                    "\nNumber of arguments: 1" +
                    "\n   Argument: element (MusicBand)\n";


    public static String execute(ConcurrentSkipListMap<Integer, MusicBand> collection, Connection connection, User user, MusicBand musicBand) {

        checkCollectionForEmptiness(collection);

        ArrayList<Integer> ownedMusicBandsByUser = DataBaseManager.getOwnedMusicBandsByUser(connection, user);
//        ArrayList<Integer> musicBandsIDsToRemove = new ArrayList<>();

        collection.values().forEach(mb -> {
                    if (mb.isMoreThan(musicBand)
                            & ownedMusicBandsByUser.contains(mb.getId())) {
                        DataBaseManager.removeMusicBand(connection, mb);
                        DataBaseManager.removeOwnedMusicBand(connection, user, mb.getId());
                        collection.remove(mb.getId());
//                        musicBandsIDsToRemove.add(mb.getId());

                    }
                });

//        musicBandsIDsToRemove.forEach(collection::remove);

        return "Reply: MusicBand greater than the specified were successfully removed if presented.";

    }

}
