package tiny;

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
  //  private final int DO_NOTHING = 0;
    private final int GO_TO_LIBRARY = 0;
    private final int EXIT_LIBRARY = 1;
    private final int GO_TO_READINGROOM = 2;
    private final int EXIT_READINGROOM = 3;
    private final int GO_HOME  = 4;
    private final int PICK_A_BOOK = 5;
    private final int READ_A_BOOK = 6;
    private final int RETURN_BOOK = 7;
    private final int GO_TO_BUILDING = 8;

    HashMap<String, int[]> movementMap = new HashMap<String, int[]>();



    Reader(Library library, Room readingRoom, String name) {
        this.library = library;
        this.readingRoom = readingRoom;
        this.name = name;
        location = "home";
        books = new ArrayList<Book>();

        movementMap.put("home", new int[]{ 6, 8});
        movementMap.put("library", new int[]{ 1, 5, 7});
        movementMap.put("building", new int[]{ 0, 2, 4});
        movementMap.put("reading room", new int[]{ 3, 6});
    }

    private int chooseAction() {
        for(String loc : movementMap.keySet()) {
            if(loc.equals(location)) {
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
                        library.giveBook(Reader.this, library.getBooks().
                                                            get(new Random().nextInt(library.
                                                                    getBooks().size())));
                        Thread.sleep(2000);
                        break;
                    case 6:
                        if(Reader.this.books.size() > 0) {
                            System.out.println("Reader " + this.name + " is reading");
                            Thread.sleep(10000);
                        }
                        else break;
                        break;
                    case 7:
                        if(Reader.this.books.size() > 0) {
                            library.returnBook(Reader.this, Reader.this.getBooks().
                                    get(new Random().nextInt(Reader.this.
                                            getBooks().size())));
                        }
                        else break;
                        break;
                    case 8:
                        Reader.this.setLocation("building");
                        System.out.println("Reader " + Reader.this.name + " entered building");
                        break;
                }
            }
        }catch (InterruptedException ignored){}
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
 /*
 *   try {
            library.enter(Reader.this);
            Thread.sleep(2000);
            library.giveBook(Reader.this, library.getBooks().get(new Random().nextInt(library.getBooks().size())));
            Thread.sleep(1000);
            library.leave(Reader.this);
            readingRoom.enter(Reader.this);
            Thread.sleep(5000);
            readingRoom.leave(Reader.this);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } */