package ru.itmo.core.command;

import ru.itmo.core.common.classes.MusicBand;
import ru.itmo.core.exception.InvalidCommandException;

import java.util.concurrent.ConcurrentSkipListMap;

public abstract class Command {


    protected static void checkCollectionForEmptiness(ConcurrentSkipListMap<Integer, MusicBand> collection) {
    if (collection.size() == 0)
        throw new InvalidCommandException("Error: Collection is empty. Impossible to run the command.");
    }
}
