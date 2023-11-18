package com.owncloud.android.ui.helpers;

/*
    Nextcloud Android client application

    Copyright (C) 2023 Chabeli Castano for FIU senior project

    This program is free software; you can redistribute it and/or
    modify it under the terms of the GNU AFFERO GENERAL PUBLIC LICENSE
    License as published by the Free Software Foundation; either
    version 3 of the License, or any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU AFFERO GENERAL PUBLIC LICENSE for more details.

    You should have received a copy of the GNU Affero General Public
    License along with this program.  If not, see http://www.gnu.org/licenses/

 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.owncloud.android.utils.CalendarEvent;

import java.util.ArrayList;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "calendar_db";
    private static final int DATABASE_VERSION = 2;
    //table
    private static final String CAL_TABLE = "calendar_event_table";
    //Table Columns
    private static final String KEY_EVENT_DESCRIPTION = "description";
    private static final String KEY_EVENT_DATE_TIME = "date_time";
    private static final String KEY_EVENT_LOC = "loc";
    private static final String KEY_EVENT_USER = "user_id";
    private static final String KEY_DISPLAYABLE_STR = "diplayable_event";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //db connection
    @Override
    public void onConfigure(SQLiteDatabase database) {
        super.onConfigure(database);
        database.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + CAL_TABLE +
            "("
            + "event_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            KEY_EVENT_DESCRIPTION + " TEXT," +
            KEY_EVENT_DATE_TIME + " TEXT," +
            KEY_EVENT_LOC + " TEXT," +
            KEY_DISPLAYABLE_STR + " TEXT, " +
            KEY_EVENT_USER + " TEXT" + ")";
        database.execSQL(createTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //implement update logic later if needed
    }

    //save events to db
    public void saveEventDetails(String description, String datetime, String location, String displayableEvent, String user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // get the values
        values.put(KEY_EVENT_DESCRIPTION, description);
        values.put(KEY_EVENT_DATE_TIME, datetime);
        values.put(KEY_EVENT_LOC, location);
        values.put(KEY_DISPLAYABLE_STR, displayableEvent);
        values.put(KEY_EVENT_USER, user);

        //insert the values into the database
        database.insert(CAL_TABLE, null, values);

        //close the db
        database.close();
    }

    //retrieve elements from the database
    public ArrayList<String> getDisplayableEvents(String dateSubstr, String user) {
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<String> eventsList = new ArrayList<>();

        if (isTableExists(database, CAL_TABLE)) {
            Cursor cursor = database.rawQuery("SELECT " + KEY_DISPLAYABLE_STR + " FROM " + CAL_TABLE + " WHERE "
                                                  + KEY_EVENT_DATE_TIME + " LIKE " + "\'%" + dateSubstr + "%\' AND "
                                                  + KEY_EVENT_USER + " = \'" + user + "\'", null);

            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String eventData = cursor.getString(cursor.getColumnIndexOrThrow(KEY_DISPLAYABLE_STR));
                    System.out.println(eventData);
                    eventsList.add(eventData);
                }
                cursor.close();
            } else {
                eventsList.add("No Events");
            }
            database.close();
        } else {
                eventsList.add("No table");
        }
        return eventsList;
        }


    private boolean isTableExists(SQLiteDatabase database, String tableName) {
        Cursor cursor = database.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tableName});
        try {
            return cursor.getCount() > 0;
        } finally {
            cursor.close();
        }
    }

    public String[] getInfoBasedOnDisplayedEvent (String displayedEvent, String user){
        SQLiteDatabase database = this.getReadableDatabase();
        //array
        String[] eventInfo = new String [4];

        Cursor cursor = database.rawQuery("SELECT event_id, " + KEY_EVENT_DESCRIPTION + ", " + KEY_EVENT_DATE_TIME + ", " + KEY_EVENT_LOC
                                            + " FROM " + CAL_TABLE + " WHERE " + KEY_DISPLAYABLE_STR + " = \'" + displayedEvent + "\'" +
                                              " AND "
                                              + KEY_EVENT_USER + " = \'" + user + "\'", null);

        try {
            if (cursor != null && cursor.moveToNext()) {
                String description = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_DESCRIPTION));
                String dateTime = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_DATE_TIME));
                String location = cursor.getString(cursor.getColumnIndexOrThrow(KEY_EVENT_LOC));
                int key = cursor.getInt(cursor.getColumnIndexOrThrow("event_id"));

                eventInfo[0] = description;
                eventInfo[1] = dateTime;
                eventInfo[2] = location;
                eventInfo[3] = String.valueOf(key);

                cursor.close();

            } else {
                eventInfo[0] = "0";
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        database.close();

        return eventInfo;
    }

    //modify events
    public void modifyEvent(int primaryKey, String description, String dateTime, String location, String displayableEvent){
        SQLiteDatabase databaseReadable = this.getReadableDatabase();
        SQLiteDatabase databaseWritable = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EVENT_DESCRIPTION, description);
        contentValues.put(KEY_EVENT_DATE_TIME, dateTime);
        contentValues.put(KEY_EVENT_LOC, location);
        contentValues.put(KEY_DISPLAYABLE_STR, displayableEvent);

        databaseWritable.update(CAL_TABLE, contentValues, "event_id" + " = ?", new String[]{String.valueOf(primaryKey)});

        databaseWritable.close();
        databaseReadable.close();
    }

    public void deleteEvent(int primaryKey){
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(CAL_TABLE, "event_id" + " = ?", new String[]{String.valueOf(primaryKey)});

        database.close();
    }


}
