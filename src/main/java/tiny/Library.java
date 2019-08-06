package tiny;

import java.util.ArrayList;
import java.util.Set;

public class Library implements Room {
    private int readersInRoom = 0;
    private int maxReadersInRoom = 2;
    private int minReadersInRoom = 0;


    private ArrayList<Book> books;

    public synchronized void enter(Reader reader) {
        while (readersInRoom >= maxReadersInRoom) {
            try {
                wait();
            }
            catch (InterruptedException ignored){}
        }
        readersInRoom++;
        reader.setLocation("library");
        System.out.println("Reader " + reader.getName() + " entered to library");
        System.out.println("Readers in library room: " + readersInRoom);
        notify();
    }

    public synchronized void leave(Reader reader) {
        while (readersInRoom <= minReadersInRoom) {
            try {
                wait();
            }
            catch (InterruptedException ignored){}
        }
        readersInRoom--;
        reader.setLocation("building");
        System.out.println("Reader " + reader.getName() + " leaved library");
        System.out.println("Readers in library room: " + readersInRoom);
        notify();
    }


    public synchronized void giveBook(Reader reader, Book book) {
        ArrayList<Book> books = reader.getBooks();
        books.add(book);
        reader.setBooks(books);
        books.remove(book);
        System.out.println("Reader " + reader.getName() + " has token the book "
                + book.getId());
    }

    public synchronized void returnBook(Reader reader, Book book) {
        ArrayList<Book> books = reader.getBooks();
        books.remove(book);
        reader.setBooks(books);
        books.add(book);
        System.out.println("Reader " + reader.getName() + " has returned the book "
                + book.getId());
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }
}
