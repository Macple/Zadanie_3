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
    private static final String CREATE_TABLE = "create table "
            + TABLE_TYP
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NAZWA + " text not null"
            + ");";

    private static final String TYP_1_INSERT = "insert into "
            + TABLE_TYP
            + " values "
            + "('1', 'jedzenie');";

    private static final String TYP_2_INSERT = "insert into "
            + TABLE_TYP
            + " values "
            + "('2', 'picie');";

    private static final String TYP_3_INSERT = "insert into "
            + TABLE_TYP
            + " values "
            + "('3', 'opłaty');";

    public static void onCreate(SQLiteDatabase datebase) {
        datebase.execSQL(CREATE_TABLE);
        datebase.execSQL(TYP_1_INSERT);
        datebase.execSQL(TYP_2_INSERT);
        datebase.execSQL(TYP_3_INSERT);
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
