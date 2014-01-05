package pl.plewko.android.zadanie3.contentprovider;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import pl.plewko.android.zadanie3.datebase.WydatkiDatebaseHelper;
import pl.plewko.android.zadanie3.datebase.WydatekTable;

/**
 * Created by Maciek on 02.01.14.
 */
public class WydatkiContentProvider extends ContentProvider {

    // database
    private WydatkiDatebaseHelper database;

    // used for the UriMatcher
    private static final int WYDATKI = 10;
    private static final int WYDATEK_ID = 20;

    private static final String AUTHORITY = "pl.plewko.android.zadanie3.contentprovider";

    private static final String BASE_PATH = "zadanie3";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
            + "/wydatki";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
            + "/wydatek";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, WYDATKI);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", WYDATEK_ID);
    }

    @Override
    public boolean onCreate() {
        database = new WydatkiDatebaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Using SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        // check if the caller has requested a column which does not exists
        checkColumns(projection);

        // Set the tables
        queryBuilder.setTables(WydatekTable.TABLE_WYDATEK);

        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case WYDATKI:
                break;
            case WYDATEK_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(WydatekTable.COLUMN_ID + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        long id = 0;
        switch (uriType) {
            case WYDATKI:
                id = sqlDB.insert(WydatekTable.TABLE_WYDATEK, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case WYDATKI:
                rowsDeleted = sqlDB.delete(WydatekTable.TABLE_WYDATEK, selection,
                        selectionArgs);
                break;
            case WYDATEK_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(WydatekTable.TABLE_WYDATEK,
                            WydatekTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(WydatekTable.TABLE_WYDATEK,
                            WydatekTable.COLUMN_ID + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case WYDATKI:
                rowsUpdated = sqlDB.update(WydatekTable.TABLE_WYDATEK,
                        values,
                        selection,
                        selectionArgs);
                break;
            case WYDATEK_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(WydatekTable.TABLE_WYDATEK,
                            values,
                            WydatekTable.COLUMN_ID + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(WydatekTable.TABLE_WYDATEK,
                            values,
                            WydatekTable.COLUMN_ID + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = { WydatekTable.COLUMN_NAZWA,
                WydatekTable.COLUMN_DATA, WydatekTable.COLUMN_TYP, WydatekTable.COLUMN_WARTOSC,
                WydatekTable.COLUMN_ID };
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

}
