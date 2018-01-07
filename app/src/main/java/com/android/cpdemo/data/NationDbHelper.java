package com.android.cpdemo.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class NationDbHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "nations.db";
	private static final int DATABASE_VERSION = 2;
	private static final String TAG = "NationDbHelper";

	private final String SQL_CREATE_COUNTRY_TABLE
			= "CREATE TABLE " + NationContract.NationEntry.TABLE_NAME
			+ " (" + NationContract.NationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NationContract.NationEntry.COLUMN_COUNTRY + " TEXT NOT NULL, "
			+ NationContract.NationEntry.COLUMN_CONTINENT + " TEXT"
			+ ");";


	// TODO: 07/01/18 This is called to create the database
	public NationDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		Log.d(TAG, "NationDbHelper: is called");
	}


	// TODO: 07/01/18 onCreate method only be called when  nations.db file dosent exist.
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_COUNTRY_TABLE);
		Log.d(TAG, "onCreate: is called");
		
	}

	// TODO: 07/01/18 onUpgrade is called when nations.db file exists and database version is upgraded
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "onUpgrade: is called with OldVersion"+ oldVersion);
		Log.d(TAG, "onUpgrade: is called with newVersion"+ newVersion);
//		db.execSQL("drop the table countries");
//		db.execSQL("create table countries with updated column");
	}
}



/*
		TABLE NAME: countries	Database Name: nations.db

		 _id	country		continent
 		  1		 India		 Asia
 		  2		 Japan		 Asia
 		  3		 Nigeria	 Africa
* */
