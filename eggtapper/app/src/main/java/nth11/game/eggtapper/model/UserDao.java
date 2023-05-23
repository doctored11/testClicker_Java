package nth11.game.eggtapper.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUser(BdUser user);

    @Update
    void updateUser(BdUser user);

    @Query("SELECT * FROM Accounts WHERE name = :name")
    BdUser getUser(String name);

    @Query("SELECT * FROM Accounts")
    List<BdUser> getAllUsers();

    @Query("DELETE FROM Accounts")
    void deleteAllUsers();

    @Query("SELECT password FROM Accounts WHERE name = :name")
    String getPasswordByName(String name);

    @Query("SELECT name FROM Accounts")
    List<String> getAllUserNames();
}