package pl.plewko.android.zadanie3.datebase;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    private static final String CREATE_TABLE = "create table "
            + TABLE_WYDATEK
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_TYP + " integer not null,"
            + COLUMN_NAZWA + " text not null,"
            + COLUMN_DATA + " text not null,"
            + COLUMN_WARTOSC + " text not null,"
            + " foreign key (" + COLUMN_TYP + ") references "+ TypTable.TABLE_TYP
            + " (" + TypTable.COLUMN_ID + "));";

    private static final String WYDATEK_1_INSERT = "insert into "
            + TABLE_WYDATEK
            + " values "
            + "('1', '2', 'cola', '14.07.2013', '2.50');";

    private static final String WYDATEK_2_INSERT = "insert into "
            + TABLE_WYDATEK
            + " values "
            + "('2', '1', 'pizza', '29.11.2013', '23.30');";

    private static final String WYDATEK_3_INSERT = "insert into "
            + TABLE_WYDATEK
            + " values "
            + "('3', '3', 'czesne', '05.02.2013', '520.00');";

    private static final String WYDATEK_4_INSERT = "insert into "
            + TABLE_WYDATEK
            + " values "
            + "('4', '1', 'piwo', '19.08.2013', '7.50');";

    public static void onCreate(SQLiteDatabase datebase) {
        datebase.execSQL(CREATE_TABLE);
        datebase.execSQL(WYDATEK_1_INSERT);
        datebase.execSQL(WYDATEK_2_INSERT);
        datebase.execSQL(WYDATEK_3_INSERT);
        datebase.execSQL(WYDATEK_4_INSERT);
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
