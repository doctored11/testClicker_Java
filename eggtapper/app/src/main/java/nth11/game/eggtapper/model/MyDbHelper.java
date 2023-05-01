package nth11.game.eggtapper.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDbHelper extends SQLiteOpenHelper {

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
    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_MONEY + " INTEGER, " +
                COLUMN_STRENGTH + " INTEGER, " +
                COLUMN_TOOL_FORCE + " INTEGER, " +
                COLUMN_TOOL_PROFIT + " INTEGER, " +
                COLUMN_TOOL_UPCOAST_PROFIT + " INTEGER, " +
                COLUMN_TOOL_UPCOAST_FORCE + " INTEGER, " +
                COLUMN_INCUBATOR_FORCE + " INTEGER, " +
                COLUMN_INCUBATOR_PROFIT + " INTEGER, " +
                COLUMN_INCUBATOR_UPCOAST_PROFIT + " INTEGER, " +
                COLUMN_INCUBATOR_UPCOAST_FORCE + " INTEGER, " +
                COLUMN_COUNT_TAP_P + " INTEGER, " +
                COLUMN_COUNT_TAP_F + " INTEGER, " +
                COLUMN_COUNT_INC_P + " INTEGER, " +
                COLUMN_COUNT_INC_F + " INTEGER)";

        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    public boolean addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_MONEY, user.getMoney().getValue());
        values.put(COLUMN_STRENGTH, user.getStrength());
        values.put(COLUMN_TOOL_FORCE, user.getToolForce());
        values.put(COLUMN_TOOL_PROFIT, user.getToolProfit().getValue());
        values.put(COLUMN_TOOL_UPCOAST_PROFIT, user.getToolUpCoastProfit().getValue());
        values.put(COLUMN_TOOL_UPCOAST_FORCE, user.getToolUpCoastForce().getValue());
        values.put(COLUMN_INCUBATOR_FORCE, user.getIncubatorForce());
        values.put(COLUMN_INCUBATOR_PROFIT, user.getIncubatorProfit().getValue());
        values.put(COLUMN_INCUBATOR_UPCOAST_PROFIT, user.getIncubatorUpCoastProfit().getValue());
        values.put(COLUMN_INCUBATOR_UPCOAST_FORCE, user.getIncubatorUpCoastForce().getValue());
        values.put(COLUMN_COUNT_TAP_P, user.getCountTapP());
        values.put(COLUMN_COUNT_TAP_F, user.getCountTapF());
        values.put(COLUMN_COUNT_INC_P, user.getCountIncP());
        values.put(COLUMN_COUNT_INC_F, user.getCountIncF());
        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    public boolean updateUser(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        values.put(COLUMN_MONEY, user.getMoney().getFormattedValue());
        values.put(COLUMN_STRENGTH, user.getStrength());
        values.put(COLUMN_TOOL_FORCE, user.getToolForce());
        values.put(COLUMN_TOOL_PROFIT, user.getToolProfit().getFormattedValue());
        values.put(COLUMN_TOOL_UPCOAST_PROFIT, user.getToolUpCoastProfit().getFormattedValue());
        values.put(COLUMN_TOOL_UPCOAST_FORCE, user.getToolUpCoastForce().getFormattedValue());
        values.put(COLUMN_INCUBATOR_FORCE, user.getIncubatorForce());
        values.put(COLUMN_INCUBATOR_PROFIT, user.getIncubatorProfit().getFormattedValue());
        values.put(COLUMN_INCUBATOR_UPCOAST_PROFIT, user.getIncubatorUpCoastProfit().getFormattedValue());
        values.put(COLUMN_INCUBATOR_UPCOAST_FORCE, user.getIncubatorUpCoastForce().getFormattedValue());
        values.put(COLUMN_COUNT_TAP_P, user.getCountTapP());
        values.put(COLUMN_COUNT_TAP_F, user.getCountTapF());
        values.put(COLUMN_COUNT_INC_P, user.getCountIncP());
        values.put(COLUMN_COUNT_INC_F, user.getCountIncF());
        int result = db.update(TABLE_NAME, values, COLUMN_NAME + " = ?", new String[]{user.getName()});
        db.close();
        return result > 0;
    }


    public String getPassword(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {COLUMN_PASSWORD};
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        String password = null;
        if (cursor.moveToNext()) {
            password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
        }

        cursor.close();
        db.close();

        return password;
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


    public User getUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_PASSWORD,
                COLUMN_MONEY,
                COLUMN_STRENGTH,
                COLUMN_TOOL_FORCE,
                COLUMN_TOOL_PROFIT,
                COLUMN_TOOL_UPCOAST_PROFIT,
                COLUMN_TOOL_UPCOAST_FORCE,
                COLUMN_INCUBATOR_FORCE,
                COLUMN_INCUBATOR_PROFIT,
                COLUMN_INCUBATOR_UPCOAST_PROFIT,
                COLUMN_INCUBATOR_UPCOAST_FORCE,
                COLUMN_COUNT_TAP_P,
                COLUMN_COUNT_TAP_F,
                COLUMN_COUNT_INC_P,
                COLUMN_COUNT_INC_F
        };
        String selection = COLUMN_NAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;
        if (cursor.moveToNext()) {
            String password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD));
            String money = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MONEY));
            long strength = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRENGTH));
            long toolForce = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TOOL_FORCE));
            String toolProfit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOOL_PROFIT));
            String toolUpCoastProfit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOOL_UPCOAST_PROFIT));
            String toolUpCoastForce = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TOOL_UPCOAST_FORCE));
            long incubatorForce = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INCUBATOR_FORCE));
            String incubatorProfit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCUBATOR_PROFIT));
            String incubatorUpCoastProfit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCUBATOR_UPCOAST_PROFIT));
            String incubatorUpCoastForce = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INCUBATOR_UPCOAST_FORCE));

            long toolTapP = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COUNT_TAP_P));
            long toolTapF = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COUNT_TAP_F));
            long incTapP = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COUNT_INC_P));
            long incTapF = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_COUNT_INC_F));
//            User(String name, String password, GameCurrency money, long strength,
//            long toolForce, GameCurrency toolProfit, GameCurrency toolUpCoastProfit,
//                    GameCurrency toolUpCoastForce, long incubatorForce, GameCurrency incubatorProfit,
//                    GameCurrency incubatorUpCoastprofit, GameCurrency incubatorUpCoastForce, long countTapP, long countTapF, long countIncP, long countIncF ) {

                user = new User(username, password, GameCurrency.parse(money), strength, toolForce,
                    GameCurrency.parse(toolProfit), GameCurrency.parse(toolUpCoastProfit), GameCurrency.parse(toolUpCoastForce),
                    incubatorForce, GameCurrency.parse(incubatorProfit), GameCurrency.parse(incubatorUpCoastProfit),
                    GameCurrency.parse(incubatorUpCoastForce),toolTapP,toolTapF,incTapP,incTapF);
        }

        cursor.close();
        db.close();
        return user;
    }
    public boolean hasRecords() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_NAME}; // используем любую колонку для запроса
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        boolean hasRecords = cursor.moveToFirst(); // true, если есть запись, false - если пустая
        cursor.close();
        db.close();
        return hasRecords;
    }
    public String getLastUserName() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        int nameIndex = cursor.getColumnIndex(COLUMN_NAME);
        String lastUserName = null;
        while (cursor.moveToNext()) {
            lastUserName = cursor.getString(nameIndex);
        }
        cursor.close();
        db.close();
        return lastUserName;
    }





}
