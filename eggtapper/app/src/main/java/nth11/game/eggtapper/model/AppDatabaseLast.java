package nth11.game.eggtapper.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LastUser.class}, version = 1)
public abstract class AppDatabaseLast extends RoomDatabase {
    public abstract LastUserDao lastUserDao();
}