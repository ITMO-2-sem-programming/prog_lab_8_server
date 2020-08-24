package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;

import java.util.concurrent.ConcurrentSkipListMap;

public class InfoCommand extends Command {

    public static String syntaxDescription =
                    "\nCommand: info" +
                    "\nDescription: Prints the information about collection." +
                    "\nNumber of arguments: 0\n";




    /**
     * @param collection
     * @param dateOfInitialisation
     * @return
     */
    public static String execute(ConcurrentSkipListMap<Integer, MusicBand> collection, String dateOfInitialisation) {

        return String.format("Collection type: TreeMap" +
                "\nCollection consists of pairs:" +
                "\n   Key:   Integer" +
                "\n   Value: MusicBand" +
                "\nNumber of elements: %s" +
                "\nDate of initialisation: %s", collection.size(), dateOfInitialisation);
    }

}
