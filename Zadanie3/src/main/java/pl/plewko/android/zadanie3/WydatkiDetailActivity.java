package pl.plewko.android.zadanie3;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import pl.plewko.android.zadanie3.contentprovider.WydatkiContentProvider;
import pl.plewko.android.zadanie3.datebase.WydatekTable;

/**
 * Created by Maciek on 02.01.14.
 * WydatkiDetailActivity allows to enter a new wydatek
 * or to change an existing
 */
public class WydatkiDetailActivity extends Activity {

    private EditText mName;
    private EditText mDate;
    private EditText mType;
    private EditText mValue;

    private Uri wydatkiUri;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.wydatki_edit);

        mName = (EditText) findViewById(R.id.wydatek_name_edit);
        mType = (EditText) findViewById(R.id.wydatek_type_edit);
        mDate = (EditText) findViewById(R.id.wydatek_date_edit);
        mValue = (EditText) findViewById(R.id.wydatek_value_edit);
        Button confirmButton = (Button) findViewById(R.id.wydatek_confirm);

        Bundle extras = getIntent().getExtras();

        // check from the saved Instance
        wydatkiUri = (bundle == null) ? null : (Uri) bundle
                .getParcelable(WydatkiContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity
        if (extras != null) {
            wydatkiUri = extras
                    .getParcelable(WydatkiContentProvider.CONTENT_ITEM_TYPE);

            fillData(wydatkiUri);
        }

        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (TextUtils.isEmpty(mName.getText().toString()) ||
                        TextUtils.isEmpty(mDate.getText().toString()) ||
                        TextUtils.isEmpty(mValue.getText().toString())) {
                    makeToast();
                } else {
                    setResult(RESULT_OK);
                    finish();
                }
            }

        });
    }

    private void fillData(Uri uri) {
        String[] projection = { WydatekTable.COLUMN_NAZWA,
                WydatekTable.COLUMN_TYP, WydatekTable.COLUMN_DATA, WydatekTable.COLUMN_WARTOSC };
        Cursor cursor = getContentResolver().query(uri, projection, null, null,
                null);
        if (cursor != null) {
            cursor.moveToFirst();

            mName.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(WydatekTable.COLUMN_NAZWA)));
            mType.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(WydatekTable.COLUMN_TYP)));
            mDate.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(WydatekTable.COLUMN_DATA)));
            mValue.setText(cursor.getString(cursor
                    .getColumnIndexOrThrow(WydatekTable.COLUMN_WARTOSC)));

            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(WydatkiContentProvider.CONTENT_ITEM_TYPE, wydatkiUri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveState();
    }

    private void saveState() {
        String nazwa = mName.getText().toString();
        String typ = mType.getText().toString();
        String data = mDate.getText().toString();
        String wartosc =  mValue.getText().toString();

        // only save if name is available
        if (nazwa.length() == 0) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(WydatekTable.COLUMN_NAZWA, nazwa);
        values.put(WydatekTable.COLUMN_TYP, typ);
        values.put(WydatekTable.COLUMN_DATA, data);
        values.put(WydatekTable.COLUMN_WARTOSC, wartosc);

        if (wydatkiUri == null) {
            // New wydatek
            wydatkiUri = getContentResolver().insert(WydatkiContentProvider.CONTENT_URI, values);
        } else {
            // Update wydatek
            getContentResolver().update(wydatkiUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(WydatkiDetailActivity.this, "Wprowadz wszystkie dane",
                2).show();
    }
}