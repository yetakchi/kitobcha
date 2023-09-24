package models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "sessions")
public class Session {

    public static final short MAIN_SELECTED = 1;
    public static final short BOOK_SELECTED = 2;
    public static final short CURRENCY_SELECTED = 3;

    @DatabaseField(generatedId = true, canBeNull = false)
    private long id;

    @DatabaseField(foreign = true)
    private User user;

    @DatabaseField(canBeNull = false, defaultValue = "0")
    private short status;

    @DatabaseField
    private String data;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id=" + id +
                ", user=" + user +
                ", status=" + status +
                ", data='" + data + '\'' +
                '}';
    }
}
