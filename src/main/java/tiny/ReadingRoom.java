package tiny;

public class ReadingRoom implements Room {
    private int readersInRoom = 0;
    private int maxReadersInRoom = 4;
    private int minReadersInRoom = 0;

    public synchronized void enter(Reader reader) {
        while (readersInRoom >= maxReadersInRoom) {
            try {
                wait();
            }
            catch (InterruptedException ignored){}
        }
        readersInRoom++;
        reader.setLocation("reading room");
        System.out.println("Reader " + reader.getName() + " entered to reading room");
        System.out.println("Readers in reading room: " + readersInRoom);
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
        System.out.println("Reader " + reader.getName() + " leaved reading room");
        System.out.println("Readers in reading room: " + readersInRoom);
        notify();
    }
}
