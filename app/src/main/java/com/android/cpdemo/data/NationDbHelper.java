package com.android.cpdemo.data;


public class NationDbHelper {

	private static final String DATABASE_NAME = "nations.db";
	private static final int DATABASE_VERSION = 1;

	private final String SQL_CREATE_COUNTRY_TABLE
			= "CREATE TABLE " + NationContract.NationEntry.TABLE_NAME
			+ " (" + NationContract.NationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ NationContract.NationEntry.COLUMN_COUNTRY + " TEXT NOT NULL, "
			+ NationContract.NationEntry.COLUMN_CONTINENT + " TEXT"
			+ ");";




}



/*
		TABLE NAME: countries	Database Name: nations.db

		 _id	country		continent
 		  1		 India		 Asia
 		  2		 Japan		 Asia
 		  3		 Nigeria	 Africa
* */
