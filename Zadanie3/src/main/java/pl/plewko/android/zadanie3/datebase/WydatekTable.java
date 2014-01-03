package pl.plewko.android.zadanie3.datebase;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Maciek on 02.01.14.
 */
public class WydatekTable {

    //Datbase Table Constant Names
    public static final String TABLE_WYDATEK = "wydatek";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TYP = "typ";
    public static final String COLUMN_NAZWA = "nazwa";
    public static final String COLUMN_DATA = "data";
    public static final String COLUMN_WARTOSC = "wartosc";

    //Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_WYDATEK
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_TYP + " text not null,"
            + COLUMN_NAZWA + " text not null,"
            + COLUMN_DATA + " text not null,"
            + COLUMN_WARTOSC + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase datebase) {
        datebase.execSQL(DATABASE_CREATE);
        Log.d("onCreate", "baza stworzona");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(WydatekTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_WYDATEK);
        onCreate(database);
    }
}
