package ua.nure.pashchenko.model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReadingRoom {
    private static int readingTables = 10;
    private static Logger readingRoomLogger = LogManager.getLogger(ReadingRoom.class.getName());

    public static boolean letReaderIn(Reader reader) {
        if (readingTables <= 0) {
            readingRoomLogger.log(Level.WARN, "Reading room is full, reader "
                    + reader.getId() + "can't enter");
            return false;
        }
        else {
            readingTables--;
            readingRoomLogger.log(Level.INFO, "Reader " + reader.getId() +
                    "entered the reading room");
            return true;
        }
    }

    public static boolean letReaderOut(Reader reader) {
        if (readingTables >= 10) {
            readingRoomLogger.log(Level.ERROR, "Reading room is empty!");
            return false;
        }
        else {
            readingTables++;
            readingRoomLogger.log(Level.INFO, "Reader " + reader.getId() +
                    "leaved the reading room");
            return true;
        }
    }
}
