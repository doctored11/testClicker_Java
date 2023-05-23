package nth11.game.eggtapper.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "lastUser")
public class LastUser {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "last_logout_time")
    private long lastLogoutTime;

    // Конструкторы, геттеры и сеттеры

    public LastUser(int id) {
        this.id = id;
    }
    @Ignore
    public LastUser(int id, String username, long lastLogoutTime) {
        this.id = id;
        this.username = username;
        this.lastLogoutTime = lastLogoutTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastLogoutTime(long lastLogoutTime) {
        this.lastLogoutTime = lastLogoutTime;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public long getLastLogoutTime() {
        return lastLogoutTime;
    }
}
