package nth11.game.eggtapper.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BDHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "game_progress.db";
    private static final int DATABASE_VERSION = 1;

    // Названия столбцов таблицы
    private static final String TABLE_NAME = "game_progress";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_MONEY = "money";
    private static final String COLUMN_TAP_TOOL_FORCE = "taptoolforce";
    private static final String COLUMN_TAP_TOOL_PROFIT = "taptoolprofit";
    private static final String COLUMN_TAP_TOOL_COAST = "taptoolcoast";
    private static final String COLUMN_INCUBATOR_FORCE = "incubatorforce";
    private static final String COLUMN_INCUBATOR_PROFIT = "incubatorprofit";
    private static final String COLUMN_INCUBATOR_COAST = "incubatorcoast";

    // Запрос на создание таблицы
    private static final String CREATE_TABLE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_MONEY + " INTEGER,"
            + COLUMN_TAP_TOOL_FORCE + " INTEGER DEFAULT 5,"
            + COLUMN_TAP_TOOL_PROFIT + " INTEGER DEFAULT 1,"
            + COLUMN_TAP_TOOL_COAST + " INTEGER DEFAULT 50,"
            + COLUMN_INCUBATOR_FORCE + " INTEGER DEFAULT 1,"
            + COLUMN_INCUBATOR_PROFIT + " INTEGER DEFAULT 1,"
            + COLUMN_INCUBATOR_COAST + " INTEGER DEFAULT 1000"
            + ")";

    public BDHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Метод вызывается при обновлении версии базы данных
        // Здесь можно реализовать миграцию данных, если это необходимо
    }

    public void saveData(long money, long tapToolForce, long tapToolProfit, long tapToolCoast,
                         long incubatorForce, long incubatorProfit, long incubatorCoast) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MONEY, money);
        values.put(COLUMN_TAP_TOOL_FORCE, tapToolForce);
        values.put(COLUMN_TAP_TOOL_PROFIT, tapToolProfit);
        values.put(COLUMN_TAP_TOOL_COAST, tapToolCoast);
        values.put(COLUMN_INCUBATOR_FORCE, incubatorForce);
        values.put(COLUMN_INCUBATOR_PROFIT, incubatorProfit);
        values.put(COLUMN_INCUBATOR_COAST, incubatorCoast);

        String[] columns = {COLUMN_MONEY, COLUMN_TAP_TOOL_FORCE, COLUMN_TAP_TOOL_PROFIT,
                COLUMN_TAP_TOOL_COAST, COLUMN_INCUBATOR_FORCE, COLUMN_INCUBATOR_PROFIT, COLUMN_INCUBATOR_COAST};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            Log.e("!save",cursor.moveToFirst()+" ");
            db.update(TABLE_NAME, values, COLUMN_ID + " = ?", new String[]{String.valueOf(1)});
        } else {
            Log.e("!save",cursor.moveToFirst()+" ");
            db.insert(TABLE_NAME, null, values);
        }
        db.close();
    }


    public static class DataReader {
        private BDHelper dbHelper;

        public DataReader(Context context) {
            dbHelper = new BDHelper(context);
        }

        @SuppressLint("Range")
        public int[] readData() {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            String[] columns = {COLUMN_MONEY, COLUMN_TAP_TOOL_FORCE, COLUMN_TAP_TOOL_PROFIT,
                    COLUMN_TAP_TOOL_COAST, COLUMN_INCUBATOR_FORCE, COLUMN_INCUBATOR_PROFIT, COLUMN_INCUBATOR_COAST};
            Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

            int[] data = new int[7];

            if (cursor.moveToFirst()) {
                data[0] = cursor.getInt(cursor.getColumnIndex(COLUMN_MONEY));
                data[1] = cursor.getInt(cursor.getColumnIndex(COLUMN_TAP_TOOL_FORCE));
                data[2] = cursor.getInt(cursor.getColumnIndex(COLUMN_TAP_TOOL_PROFIT));
                data[3] = cursor.getInt(cursor.getColumnIndex(COLUMN_TAP_TOOL_COAST));
                data[4] = cursor.getInt(cursor.getColumnIndex(COLUMN_INCUBATOR_FORCE));
                data[5] = cursor.getInt(cursor.getColumnIndex(COLUMN_INCUBATOR_PROFIT));
                data[6] = cursor.getInt(cursor.getColumnIndex(COLUMN_INCUBATOR_COAST));
            }else {
                // TODO что то сделать с этим позором
                data[0] = 0;
                data[1] = 1;
                data[2] = 1;
                data[3] = 50;
                data[4] = 1;
                data[5] = 1;
                data[6] = 1000;
            }

            cursor.close();
            db.close();

            return data;
        }
    }


}
