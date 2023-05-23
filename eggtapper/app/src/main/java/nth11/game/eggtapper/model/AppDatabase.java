package nth11.game.eggtapper.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {BdUser.class}, version = 1)
@TypeConverters(GameCurrencyConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}