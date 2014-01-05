package pl.plewko.android.zadanie3.datebase;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Maciek on 03.01.14.
 */
public class TypTable {

    //Datbase Table Constant Names
    public static final String TABLE_TYP = "typ";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAZWA = "nazwa";


    //Database creation SQL statement
    private static final String COLUMN_CREATE = "create table "
            + TABLE_TYP
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NAZWA + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase datebase) {
        datebase.execSQL(COLUMN_CREATE);
        Log.d("onCreate", "TypTable stworzona");
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(WydatekTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_TYP);
        onCreate(database);
    }
}
