package com.android.cpdemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.cpdemo.data.NationContract;
import com.android.cpdemo.data.NationDbHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	private EditText etCountry, etContinent, etWhereToUpdate, etNewContinent, etWhereToDelete, etQueryRowById;
	private Button btnInsert, btnUpdate, btnDelete, btnQueryRowById, btnDisplayAll;

	private static final String TAG = MainActivity.class.getSimpleName();
	private SQLiteDatabase sqLiteDatabase;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		NationDbHelper nationDbHelper = new NationDbHelper(this);
		sqLiteDatabase = nationDbHelper.getWritableDatabase();

		etCountry 		= (EditText) findViewById(R.id.etCountry);
		etContinent 	= (EditText) findViewById(R.id.etContinent);
		etWhereToUpdate = (EditText) findViewById(R.id.etWhereToUpdate);
		etNewContinent 	= (EditText) findViewById(R.id.etUpdateContinent);
		etQueryRowById 	= (EditText) findViewById(R.id.etQueryByRowId);
		etWhereToDelete = (EditText) findViewById(R.id.etWhereToDelete);

		btnInsert 		= (Button) findViewById(R.id.btnInsert);
		btnUpdate 		= (Button) findViewById(R.id.btnUpdate);
		btnDelete 		= (Button) findViewById(R.id.btnDelete);
		btnQueryRowById = (Button) findViewById(R.id.btnQueryByID);
		btnDisplayAll 	= (Button) findViewById(R.id.btnDisplayAll);

		btnInsert.setOnClickListener(this);
		btnUpdate.setOnClickListener(this);
		btnDelete.setOnClickListener(this);
		btnQueryRowById.setOnClickListener(this);
		btnDisplayAll.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.btnInsert:
				insert();
				break;

			case R.id.btnUpdate:
				update();
				break;

			case R.id.btnDelete:
				delete();
				break;

			case R.id.btnQueryByID:
				queryRowById();
				break;

			case R.id.btnDisplayAll:
				queryAndDisplayAll();
				break;
		}
	}

	private void insert() {

		String countryName     = etCountry.getText().toString();
		String continentName= etContinent.getText().toString();

		ContentValues contentValues = new ContentValues();
		contentValues.put(NationContract.NationEntry.COLUMN_COUNTRY, countryName);
		contentValues.put(NationContract.NationEntry.COLUMN_CONTINENT, continentName);

		long rowId = sqLiteDatabase.insert(NationContract.NationEntry.TABLE_NAME, null, contentValues);
		Log.i(TAG, "Items inserted in table with row id: " + rowId);
	}

	private void update() {

		String whereCountry = etWhereToUpdate.getText().toString();
		String newContinent = etNewContinent.getText().toString();

		String selection = NationContract.NationEntry.COLUMN_COUNTRY + " = ?";
		String[] selectionArgs = { whereCountry };       // WHERE country = ? = Japan

		ContentValues contentValues = new ContentValues();
		contentValues.put(NationContract.NationEntry.COLUMN_CONTINENT, newContinent);

		int rowsUpdated = sqLiteDatabase.update(NationContract.NationEntry.TABLE_NAME, contentValues, selection, selectionArgs);
		Log.i(TAG, "Number of rows updated: " + rowsUpdated);
	}

	private void delete() {

		String countryName = etWhereToDelete.getText().toString();

		String selection = NationContract.NationEntry.COLUMN_COUNTRY + " = ? ";
		String[] selectionArgs = { countryName };     // WHERE country = "Japan"

		int rowsDeleted = sqLiteDatabase.delete(NationContract.NationEntry.TABLE_NAME, selection, selectionArgs);
		Log.i(TAG, "Number of rows deleted: " + rowsDeleted);
	}

	private void queryRowById() {

		String rowId = etQueryRowById.getText().toString();

		String[] projection = {
				NationContract.NationEntry._ID,
				NationContract.NationEntry.COLUMN_COUNTRY,
				NationContract.NationEntry.COLUMN_CONTINENT
		};

		// Filter results. Make these null if you want to query all rows
		String selection = NationContract.NationEntry._ID + " = ? ";  // _id = ?
		String[] selectionArgs = { rowId };             // Replace '?' by rowId in runtime    // _id = 5

		String sortOrder = null;   // Ascending or Descending ...

		Cursor cursor = sqLiteDatabase.query(NationContract.NationEntry.TABLE_NAME,    // The table name
				projection,                 // The columns to return
				selection,                  // Selection: WHERE clause OR the condition
				selectionArgs,              // Selection Arguments for the WHERE clause
				null,                       // don't group the rows
				null,                       // don't filter by row groups
				sortOrder);                // The sort order

		if (cursor != null && cursor.moveToNext()) {

			String str = "";

			String[] columns = cursor.getColumnNames();
			for (String column : columns) {
				str += "\t" + cursor.getString(cursor.getColumnIndex(column));
			}
			str += "\n";

			cursor.close();
			Log.i(TAG, str);
		}
	}

	private void queryAndDisplayAll() {

		String[] projection = {
				NationContract.NationEntry._ID,
				NationContract.NationEntry.COLUMN_COUNTRY,
				NationContract.NationEntry.COLUMN_CONTINENT
		};

		// Filter results. Make these null if you want to query all rows
		String selection = null;
		String[] selectionArgs = null;

		String sortOrder = null;   // Ascending or Descending ...

		Cursor cursor = sqLiteDatabase.query(NationContract.NationEntry.TABLE_NAME,    // The table name
				projection,                 // The columns to return
				selection,                  // Selection: WHERE clause OR the condition
				selectionArgs,              // Selection Arguments for the WHERE clause
				null,                       // don't group the rows
				null,                       // don't filter by row groups
				sortOrder);                // The sort order

		if (cursor != null) {

			String str = "";
			while (cursor.moveToNext()) {  // Cursor iterates through all rows

				String[] columns = cursor.getColumnNames();
				for (String column : columns) {
					str += "\t" + cursor.getString(cursor.getColumnIndex(column));
				}
				str += "\n";
			}

			cursor.close();
			Log.i(TAG, str);
		}
	}

	@Override
	protected void onDestroy() {
		sqLiteDatabase.close();  // Close Database Connection
		super.onDestroy();
	}

}
