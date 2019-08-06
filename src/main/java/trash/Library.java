package trash;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.Set;

public class Library {
    //private static Set<Book> libraryBooks;
    private static Set<Reader> registeredReaders;
    private static int readersInRoom = 0;
    private static final int maxReadersInRoom = 200;
    private static final int minReadersInRoom = 0;

    private static Logger libraryLogger = LogManager.getLogger(Library.class.getName());


    public synchronized boolean letReaderIn(Reader reader) {
        if (readersInRoom >= maxReadersInRoom) {
            libraryLogger.log(Level.WARN, "Library room is full, reader "
                    + reader.getId() + "can't enter");
            return false;
        }
        else {
            readersInRoom++;
            libraryLogger.log(Level.INFO, "Reader " + reader.getId() +
                    "entered the library");
            return true;
        }
    }

    public synchronized boolean letReaderOut(Reader reader) {
        if (readersInRoom <= minReadersInRoom) {
            libraryLogger.log(Level.ERROR, "Library is empty!");
            return false;
        }
        else {
            readersInRoom--;
            libraryLogger.log(Level.INFO, "Reader " + reader.getId() +
                    "leaved the library room");
            notifyAll();
            return true;
        }
    }



    public Set<Reader> getRegisteredReaders() {
        return registeredReaders;
    }

    public void setRegisteredReaders(Set<Reader> registeredReaders) {
        Library.registeredReaders = registeredReaders;
    }

}
