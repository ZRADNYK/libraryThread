package trash;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Reader implements Runnable {
    private long id;
    private Library library = new Library();
    private String location;
    private static Logger readerLogger = LogManager.getLogger(Reader.class.getName());

    // actions
    //   private final int DO_NOTHING = 0;
    private final int GO_TO_LIBRARY = 0;
    private final int EXIT_LIBRARY = 1;


    public Reader(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    private int getAction() {
        return new Random().nextInt(1);
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void run() { // fixme варианты перемещения, таймер/цикл
        System.out.println(this.id);
        int action = getAction();
        while (true) {
            try {
                switch (action) {
                    case 0:
                        boolean enteredToLibrary = library.letReaderIn(Reader.this);
                        setLocation("Library");
                        readerLogger.log(Level.INFO, "Reader " + this.id + " entered the library");
                        if (!enteredToLibrary) {
                            Thread.currentThread().wait();
                            readerLogger.log(Level.INFO, "Reader " + this.id + " is waiting");
                        } else break;
                    case 1:
                        library.letReaderOut(Reader.this);
                        readerLogger.log(Level.INFO, "Reader " + this.id + " left the library");
                        setLocation("Building");
                        break;
                }
            } catch (InterruptedException e) {
                readerLogger.log(Level.ERROR, e.getStackTrace());
            }
        }
    }
}

