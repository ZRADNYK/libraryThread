package ua.nure.pashchenko.model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.Set;

public class Reader implements Runnable {
    private long id;
    private Set<Book> books;
    private String location;
    private static Logger readerLogger = LogManager.getLogger(ReadingRoom.class.getName());

    // actions
    private final int DO_NOTHING = 0;
    private final int GO_TO_LIBRARY = 1;
    private final int EXIT_LIBRARY = 2;
    private final int GO_TO_READINGROOM = 3;
    private final int EXIT_READINGROOM = 4;
    private final int GO_HOME  = 5;
    private final int PICK_A_BOOK = 6;
    private final int READ_A_BOOK = 7;
    private final int RETURN_BOOK = 8;

    public Reader(long id){
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    private int getAction() {
        return new Random().nextInt( 6);
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public void run() { // fixme варианты перемещения, таймер/цикл
        int action = getAction();
        try {
            switch (action) {
                case 0:
                    Thread.sleep(2000);
                    readerLogger.log(Level.INFO, "Reader " + this.id + " is having rest");
                    break;
                case 1:
                    boolean enteredToLibrary = Library.letReaderIn(Reader.this);
                    setLocation("Library");
                    readerLogger.log(Level.INFO, "Reader " + this.id + " entered the library");
                    if(!enteredToLibrary) {
                        Thread.currentThread().wait();
                        readerLogger.log(Level.INFO, "Reader " + this.id + " is waiting");
                    }
                    else break;
                case 2:
                    Library.letReaderOut(Reader.this);
                    readerLogger.log(Level.INFO, "Reader " + this.id + " left the library");
                    setLocation("Building");
                    break;
                case 3:
                    boolean enteredToReadingRoom = ReadingRoom.letReaderIn(Reader.this);
                    if(!enteredToReadingRoom) {
                        Thread.currentThread().wait();
                        readerLogger.log(Level.INFO, "Reader " + this.id + " is waiting");
                    }
                    else setLocation("Reading room");
                    readerLogger.log(Level.INFO, "Reader " + this.id + " entered the reading room");
                    break;
                case 4:
                    ReadingRoom.letReaderOut(Reader.this);
                    readerLogger.log(Level.INFO, "Reader " + this.id + " left the library");
                    setLocation("Building");
                    break;
                case 5:
                    Thread.sleep(10000);
                    setLocation("Home");
                    readerLogger.log(Level.INFO, "Reader " + this.id + " is at home");
                    break;
                case 6:
                    Set<Book> books = Library.getLibraryBooks();
                    Object[] arr = books.toArray();
                    Book book = (Book) arr[new Random().nextInt(arr.length - 1)];
                    Library.giveBook(Reader.this, book);
                    readerLogger.log(Level.INFO, "Reader " + this.id + " get the book " +
                                                        book.getId());
                    break;
                case 7:
                    Thread.sleep(10000);
                    readerLogger.log(Level.INFO, "Reader " + this.id + " is reading");
                    break;
                case 8:
                    Object[] array = Reader.this.books.toArray();
                    Book bookToReturn = (Book) array[new Random().nextInt(array.length - 1)];
                    Library.returnBook(Reader.this, bookToReturn);
                    readerLogger.log(Level.INFO, "Reader " + this.id + " returned the book " +
                            bookToReturn.getId());
            }
        }
        catch (InterruptedException e) {
            readerLogger.log(Level.ERROR, e.getStackTrace());
        }
    }
}

