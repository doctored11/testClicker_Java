package nth11.game.eggtapper.model.bd;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import nth11.game.eggtapper.model.currency.GameCurrencyConverter;

@Database(entities = {BdUser.class}, version = 1)
@TypeConverters(GameCurrencyConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}