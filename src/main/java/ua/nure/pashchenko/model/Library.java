package ua.nure.pashchenko.model;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

public class Library {
    private static Set<Book> libraryBooks;
    private static Set<Reader> registeredReaders;
    private static int readersInRoom = 10;

    private static Logger libraryLogger = LogManager.getLogger(Library.class.getName());

    public Library(Set<Book> books, Set<Reader> readers) {
        libraryBooks = books;
        registeredReaders = readers;
    }

    public static synchronized void registerReader(Reader reader) {
        registeredReaders.add(reader);
        libraryLogger.log(Level.INFO, "Reader " + reader.getId() + " has been registered");
    }

    public static synchronized void giveBook(Reader reader, Book book) {
        Set<Book> books = reader.getBooks();
        books.add(book);
        reader.setBooks(books);
        libraryBooks.remove(book);
        libraryLogger.log(Level.INFO, "Reader " + reader.getId() + " has token the book "
                                        + book.getId());
    }

    public static synchronized void returnBook(Reader reader, Book book) {
        Set<Book> books = reader.getBooks();
        books.remove(book);
        reader.setBooks(books);
        libraryBooks.add(book);
        libraryLogger.log(Level.INFO, "Reader " + reader.getId() + " has returned the book "
                                        + book.getId());
    }

    public static synchronized boolean letReaderIn(Reader reader) {
        if (readersInRoom >= 10) {
            libraryLogger.log(Level.WARN, "Library room is full, reader "
                    + reader.getId() + "can't enter");
            return false;
        }
        else {
            readersInRoom++;
            libraryLogger.log(Level.INFO, "Reader " + reader.getId() +
                    "entered the reading room");
            return true;
        }
    }

    public static synchronized boolean letReaderOut(Reader reader) {
        if (readersInRoom <= 0) {
            libraryLogger.log(Level.ERROR, "Reading room is empty!");
            return false;
        }
        else {
            readersInRoom--;
            libraryLogger.log(Level.INFO, "Reader " + reader.getId() +
                    "leaved the library room");
            return true;
        }
    }



    public static synchronized Set<Book> getLibraryBooks() {
        return libraryBooks;
    }

    public synchronized void setLibrsaryBooks(Set<Book> libraryBooks) {
        Library.libraryBooks = libraryBooks;
    }

    public Set<Reader> getRegisteredReaders() {
        return registeredReaders;
    }

    public void setRegisteredReaders(Set<Reader> registeredReaders) {
        Library.registeredReaders = registeredReaders;
    }

}
