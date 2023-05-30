package nth11.game.eggtapper.model.bd;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;


@Dao
public interface LastUserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLastLoggedInUser(LastUser lastUser);

    @Query("SELECT username FROM lastUser ORDER BY last_logout_time DESC LIMIT 1")
    String getLastLoggedInUser();

    @Query("SELECT last_logout_time FROM lastUser ORDER BY last_logout_time DESC LIMIT 1")
    long getLastLogoutTime();
}

