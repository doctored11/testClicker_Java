package nth11.game.eggtapper.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MyDbHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDb";
    private static final String TABLE_NAME = "Users";

    // User table columns
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_STRENGTH = "strength";
    private static final String COLUMN_TOOL_FORCE = "tool_force";
    private static final String COLUMN_TOOL_PROFIT = "tool_profit";
    private static final String COLUMN_TOOL_UPCOAST_PROFIT = "tool_up_coast_profit";
    private static final String COLUMN_TOOL_UPCOAST_FORCE = "tool_up_coast_force";
    private static final String COLUMN_INCUBATOR_FORCE = "incubator_force";
    private static final String COLUMN_INCUBATOR_PROFIT = "incubator_profit";
    private static final String COLUMN_INCUBATOR_UPCOAST_PROFIT = "incubator_up_coast_profit";
    private static final String COLUMN_INCUBATOR_UPCOAST_FORCE = "incubator_up_coast_force";
    // New columns
    private static final String COLUMN_COUNT_TAP_P = "count_tap_p";
    private static final String COLUMN_COUNT_TAP_F = "count_tap_f";
    private static final String COLUMN_COUNT_INC_P = "count_inc_p";
    private static final String COLUMN_COUNT_INC_F = "count_inc_f";
    //
    private static final String LAST_USER_TABLE_NAME = "LastUser";
    private static final String COLUMN_LAST_USER_NAME = "lastUserName";
    private static final String COLUMN_LAST_LOGOUT_TIME = "lastLogoutTime";
    Context context;
    AppDatabase db;
  AppDatabaseLast dblUser;



    public MyDbHelper(Context context) {
        super();
        this.context = context;
        this.db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();
    }


    public void onCreate(Context context) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();
        dblUser = Room.databaseBuilder(context, AppDatabaseLast.class, "LastUser").build();
    }


    public void onUpgrade(User user) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();
        new AsyncTask<User, Void, Void>() {

            @Override
            protected Void doInBackground(User... users) {
                User user = users[0];
                BdUser bdUser = new BdUser(user.getName(), user.getPassword(), user.getMoney().getFormattedValue(), (int) user.getStrength(), (int) user.getToolForce(), user.getToolProfit().getFormattedValue(), user.getToolUpCoastProfit().getFormattedValue(), user.getToolUpCoastForce().getFormattedValue(), (int) user.getIncubatorForce(), user.getIncubatorProfit().getFormattedValue(), user.getIncubatorUpCoastProfit().getFormattedValue(), user.getIncubatorUpCoastForce().getFormattedValue(), (int) user.getCountTapP(), (int) user.getCountTapF(), (int) user.getCountIncP(), (int) user.getCountIncF());

                Log.e("name", user.getName());
                db.userDao().updateUser(bdUser);
                return null;
            }
        }.execute(user);
    }

    public void addUser(User user) {
        new AsyncTask<User, Void, Void>() {
            @Override
            protected Void doInBackground(User... users) {
                User user = users[0];
                BdUser bdUser = new BdUser(user.getName(), user.getPassword(), user.getMoney().getFormattedValue(), (int) user.getStrength(), (int) user.getToolForce(), user.getToolProfit().getFormattedValue(), user.getToolUpCoastProfit().getFormattedValue(), user.getToolUpCoastForce().getFormattedValue(), (int) user.getIncubatorForce(), user.getIncubatorProfit().getFormattedValue(), user.getIncubatorUpCoastProfit().getFormattedValue(), user.getIncubatorUpCoastForce().getFormattedValue(), (int) user.getCountTapP(), (int) user.getCountTapF(), (int) user.getCountIncP(), (int) user.getCountIncF());
                db.userDao().addUser(bdUser);
                return null;
            }
        }.execute(user);
    }

    public void updateUser(User user) {
        onUpgrade(user);
    }

    public boolean isUsernameExists(String username) {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "Users").allowMainThreadQueries().build();
        BdUser bdUser = db.userDao().getUser(username);
        return bdUser != null;
    }

    public int getRegisteredUserCount() {
        this.db = Room.databaseBuilder(context, AppDatabase.class, "Users").allowMainThreadQueries().build();
        List<BdUser> userList = db.userDao().getAllUsers();
        return userList.size();
    }


    public void getPassword(String username, PasswordCallback callback) {
        @SuppressLint("StaticFieldLeak") AsyncTask<String, Void, String> getPasswordTask = new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... usernames) {
                String username = usernames[0];
                db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();
                return db.userDao().getPasswordByName(username);
            }

            @Override
            protected void onPostExecute(String password) {
                //  метод обратного вызова и передаем полученный пароль
                callback.onPasswordReceived(password);
            }
        };

        getPasswordTask.execute(username);
    }


    public static String getColumnName() {
        return COLUMN_NAME;
    }

    public static String getColumnPassword() {
        return COLUMN_PASSWORD;
    }

    public static String getTableName() {
        return TABLE_NAME;
    }


    public void getUser(String username, GetUserCallback callback) {
        AsyncTask<String, Void, User> getUserTask = new AsyncTask<String, Void, User>() {
            @Override
            protected User doInBackground(String... usernames) {
                // Ваш код для получения пользователя из базы данных
                String username = usernames[0];
                db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();

                if (db == null) {
                    addDefaultUser();
                    return null;
                }

                BdUser bdUser = db.userDao().getUser(username);
                User user = new User(bdUser.getName(), bdUser.getPassword(), GameCurrency.parse(bdUser.getMoney()), bdUser.getStrength(),
                        bdUser.getToolForce(), GameCurrency.parse(bdUser.getToolProfit()), GameCurrency.parse(bdUser.getToolUpCoastProfit()),
                        GameCurrency.parse(bdUser.getToolUpCoastForce()), bdUser.getIncubatorForce(), GameCurrency.parse(bdUser.getIncubatorProfit()),
                        GameCurrency.parse(bdUser.getIncubatorUpCoastProfit()), GameCurrency.parse(bdUser.getIncubatorUpCoastForce()),
                        bdUser.getCountTapP(), bdUser.getCountTapF(), bdUser.getCountIncP(), bdUser.getCountIncF());

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                // Передаем полученного пользователя в обратный вызов
                callback.onUserReceived(user);
            }
        };

        getUserTask.execute(username);
    }


    public List<String> getUsers() {
        db = Room.databaseBuilder(context, AppDatabase.class, "Users").allowMainThreadQueries().build();
        if (db == null) {
            return Collections.emptyList();
        }

        UserDao userDao = db.userDao();
//        BdUser user = new BdUser("username", "password", "101", 10, 5, "20", "30", "40", 0, "0", "70", "80", 0, 0, 0, 0);
//        userDao.addUser(user);

        List<String> userNames = Collections.emptyList();
        try {
            if (userDao.getAllUsers().size() > 0) {
                userNames = userDao.getAllUserNames();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userNames;
    }

    public void addDefaultUser() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();
                if (db != null) {
                    UserDao userDao = db.userDao();
                    if (userDao != null) {
                        BdUser user = new BdUser("username", "password", "102", 10, 5, "20", "30", "40", 0, "0", "70", "80", 0, 0, 0, 0);
                        userDao.addUser(user);
                    }
                }
                return null;
            }
        }.execute();
    }


    public void getDefaultUser() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                db = Room.databaseBuilder(context, AppDatabase.class, "Users").build();
                if (db != null) {
                    UserDao userDao = db.userDao();
                    if (userDao != null) {
                        BdUser user = new BdUser("username", "password", "103", 10, 5, "20", "30", "40", 50, "0", "70", "80", 0, 0, 0, 0);
                        userDao.addUser(user);
                    }
                }
                return null;
            }
        }.execute();
    }


    ////
// сохранение последнего авторизованного пользователя
//    public void saveLastLoggedInUser(String username) {
//        dblUser = Room.databaseBuilder(context, AppDatabaseLast.class, "LastUser").allowMainThreadQueries().build();
//
//        Calendar calendar = Calendar.getInstance();
//        long logoutTime = calendar.getTimeInMillis();
//        LastUser lastUser = new LastUser(1);
//        lastUser.setUsername(username);
//        lastUser.setLastLogoutTime(logoutTime);
//        dblUser.lastUserDao().saveLastLoggedInUser(lastUser);
//    }
//
//    public String getLastLoggedInUser() {
//        dblUser = Room.databaseBuilder(context, AppDatabaseLast.class, "LastUser").allowMainThreadQueries().build();
//
//
//        return dblUser.lastUserDao().getLastLoggedInUser();
//    }
//
//    public long getLastLogoutTime() {
//        dblUser = Room.databaseBuilder(context, AppDatabaseLast.class, "LastUser").allowMainThreadQueries().build();
//        return dblUser.lastUserDao().getLastLogoutTime();
//    }
//


}
