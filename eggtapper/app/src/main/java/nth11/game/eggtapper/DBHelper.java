package nth11.game.eggtapper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    // Имя базы данных
    private static final String DATABASE_NAME = "game_progress";

    // Версия базы данных. Если вы измените схему базы данных, вы должны увеличить версию на 1.
    private static final int DATABASE_VERSION = 1;

    // Имя таблицы
    private static final String TABLE_NAME = "game_progress";

    // Названия столбцов
    private static final String COLUMN_MONEY_COUNT = "moneycount";
    private static final String COLUMN_TOOL_TAP_FORCE = "tooltapforce";
    private static final String COLUMN_TOOL_TAP_COST = "tooltapcost";
    private static final String COLUMN_TAP_TOOL_PROFIT = "taptoolprofit";
    private static final String COLUMN_INCUBATOR_FORCE = "incubatorforce";
    private static final String COLUMN_INCUBATOR_COST = "incubatorcost";
    private static final String COLUMN_INCUBATOR_PROFIT = "incubatorprofit";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Строка для создания таблицы
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_MONEY_COUNT + " INTEGER,"
                + COLUMN_TOOL_TAP_FORCE + " INTEGER,"
                + COLUMN_TOOL_TAP_COST + " INTEGER,"
                + COLUMN_TAP_TOOL_PROFIT + " INTEGER,"
                + COLUMN_INCUBATOR_FORCE + " INTEGER,"
                + COLUMN_INCUBATOR_COST + " INTEGER,"
                + COLUMN_INCUBATOR_PROFIT + " INTEGER"
                + ")";
        // Создание таблицы
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Удаляем старую таблицу при обновлении
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Создаём новую таблицу
        onCreate(db);
    }

    public void saveAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEY_COUNT, 9090);
        values.put(COLUMN_TOOL_TAP_FORCE, 50);
        values.put(COLUMN_TOOL_TAP_COST, 10);
        values.put(COLUMN_TAP_TOOL_PROFIT, 20);
        values.put(COLUMN_INCUBATOR_FORCE, 30);
        values.put(COLUMN_INCUBATOR_COST, 40);
        values.put(COLUMN_INCUBATOR_PROFIT, 50);

        long newRowId = db.insert(TABLE_NAME, null, values);
        Log.e("Сохранил монет: ", 9090+" $");

    }

    public int getAll() {
        Log.e("берем значения ","$$$");
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {COLUMN_MONEY_COUNT};
        Cursor cursor = db.query(TABLE_NAME, projection, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int moneyCount = cursor.getInt(cursor.getColumnIndexOrThrow(DBHelper.COLUMN_MONEY_COUNT));
            cursor.close();
            Log.e("берем значения ",moneyCount+"$");
            return moneyCount;
            // использовать значение moneyCount
        }
        cursor.close();
        return 0;
    }




}
