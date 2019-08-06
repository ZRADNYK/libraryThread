package ua.nure.pashchenko;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Reader implements Runnable {

    private String name;
    private String location;
    private Library library;
    private Room readingRoom;
    private ArrayList<Book> books;

    // actions
    /*private final int GO_TO_LIBRARY = 0;
    private final int EXIT_LIBRARY = 1;
    private final int GO_TO_READINGROOM = 2;
    private final int EXIT_READINGROOM = 3;
    private final int GO_HOME  = 4;
    private final int PICK_A_BOOK = 5;
    private final int READ_A_BOOK = 6;
    private final int RETURN_BOOK = 7;
    private final int GO_TO_BUILDING = 8; */

    private HashMap<String, int[]> movementMap = new HashMap<>();

    Reader(Library library, Room readingRoom, String name) {
        this.library = library;
        this.readingRoom = readingRoom;
        this.name = name;
        location = "home";
        books = new ArrayList<>();

        movementMap.put("home", new int[]{6, 8});
        movementMap.put("library", new int[]{1, 5, 5, 5, 7, 7});
        movementMap.put("building", new int[]{0, 0, 2, 2, 2, 2, 2});
        movementMap.put("reading room", new int[]{3, 6, 6, 6, 6, 6});
    }

    private int chooseAction() {
        for (String loc : movementMap.keySet()) {
            if (loc.equals(location)) {
                int[] moves = movementMap.get(loc);
                return moves[new Random().nextInt(moves.length)];
            }
        }
        return -1;
    }

    public void run() {
        try {
            while (true) {
                int action = chooseAction();
                switch (action) {
                    case 0:
                        library.enter(Reader.this);
                        Thread.sleep(2000);
                        break;
                    case 1:
                        library.leave(Reader.this);
                        Thread.sleep(2000);
                        break;
                    case 2:
                        readingRoom.enter(Reader.this);
                        Thread.sleep(3000);
                        break;
                    case 3:
                        readingRoom.leave(Reader.this);
                        Thread.sleep(3000);
                        break;
                    case 4:
                        setLocation("Home");
                        System.out.println("Reader " + this.name + " is at home");
                        Thread.sleep(5000);
                        break;
                    case 5:
                        if (library.getBooks().size() > 0) {
                            library.giveBook(Reader.this, library.getBooks().
                                    get(new Random().nextInt(library.
                                            getBooks().size())));
                            Thread.sleep(2000);
                            if(Reader.this.books.get(books.size() - 1).isReaderRoomOnly()) {
                                readAtReadingRoom(Reader.this.books.get(books.size() - 1));
                            }
                        }
                        break;
                    case 6:
                        if (Reader.this.books.size() > 0) {
                            System.out.println("Reader " + this.name + " is reading");
                            Thread.sleep(10000);
                        } else break;
                        break;
                    case 7:
                        if (Reader.this.books.size() > 0) {
                            library.returnBook(Reader.this, Reader.this.getBooks().
                                    get(new Random().nextInt(Reader.this.
                                            getBooks().size())));
                        } else break;
                        break;
                    case 8:
                        Reader.this.setLocation("building");
                        System.out.println("Reader " + Reader.this.name + " entered building");
                        break;
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    private void readAtReadingRoom(Book book) {
        try {
            setLocation("reading room");
            System.out.println("Reader " + this.name + " is reading the book " + book.getId() + " which is allowed to "
                                + "read only in reading room");
            Thread.sleep(10000);
            setLocation("library");
            library.returnBook(Reader.this, book);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    String getName() {
        return name;
    }


    ArrayList<Book> getBooks() {
        return books;
    }

    void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    void setLocation(String location) {
        this.location = location;
    }
}