package tiny;

import ua.nure.pashchenko.model.Reader;

public class Book {
    private long id;
    private Reader reader;
    private boolean readerRoomOnly;

    public Book(long id, boolean readerRoomOnly) {
        this.id = id;
        this.readerRoomOnly = readerRoomOnly;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }

    public boolean isReaderRoomOnly() {
        return readerRoomOnly;
    }

    public void setReaderRoomOnly(boolean readerRoomOnly) {
        this.readerRoomOnly = readerRoomOnly;
    }
}
