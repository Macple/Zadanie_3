package pl.plewko.android.zadanie3.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Maciek on 02.01.14.
 */
public class WydatkiDatebaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "wydatki.db";
    private static final int DATABASE_VERSION = 8;

    public WydatkiDatebaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        //enable foreign keys
        database.execSQL("PRAGMA foreign_keys=ON;");
        TypTable.onCreate(database);
        WydatekTable.onCreate(database);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        TypTable.onUpgrade(database, oldVersion, newVersion);
        WydatekTable.onUpgrade(database, oldVersion, newVersion);
    }

}
