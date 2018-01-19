/*
package com.example.akaash.assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.snowledge.app.model.TrackDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

*
 * Created by ganesh on 6/10/17.



public class DBHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Snowledge.db";

 //CONSTRAINTS

	private static final String PRIMARY_KEY = " PRIMARY KEY ";

 //COLUMN DATA TYPES

	private static final String INTEGER = " INTEGER ";
	private static final String TEXT = " TEXT ";
	private static final String FLOAT = " FLOAT ";

	private static DBHelper instance;
	private SQLiteDatabase mDatabase;
	private AtomicInteger mOpenCounter = new AtomicInteger();

	public static synchronized DBHelper init(Context context) {
		if (null == instance) {
			instance = new DBHelper(context);
		}
		return instance;
	}

	public DBHelper(Context context) {
		super(context.getApplicationContext(), DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		sqLiteDatabase.execSQL(QUERY_CREATE_TABLE_TRACKS);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

	}

	public synchronized SQLiteDatabase openDatabase() {
		if (mOpenCounter.incrementAndGet() == 1) {
			mDatabase = getWritableDatabase();
		}
		return mDatabase;
	}

	public synchronized void closeDatabase() {
		if (mOpenCounter.decrementAndGet() == 0) {
			if (null != mDatabase && mDatabase.isOpen()) {
				mDatabase.close();
			}
		}
	}

=========================================================================================

	//  Tracks functionality
=========================================================================================


	private static final String SEGMENT_ID = "segment_id";
	private static final String TABLE_TRACKS = "tracks_table";
	private static final String TIMESTAMP = "timestamp";
	private static final String TYPE = "type";
	private static final String GEOMETRY_TYPE = "geometry_type";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String ALTITUDE = "altitude";
	private static final String VERTICAL_ACCURACY = "vertical_accuracy";
	private static final String HORIZONTAL_ACCURACY = "horizontal_accuracy";
	private static final String VERTICAL_FEET = "vertical_feet";
	private static final String RUNS = "runs";

	private static final String QUERY_CREATE_TABLE_TRACKS = "CREATE TABLE " + TABLE_TRACKS
			+ " ( "
			+ TIMESTAMP + TEXT + PRIMARY_KEY + " , "
			+ SEGMENT_ID + INTEGER + " , "
			+ TYPE + TEXT + " , "
			+ GEOMETRY_TYPE + TEXT + " , "
			+ LATITUDE + TEXT + " , "
			+ LONGITUDE + TEXT + " , "
			+ ALTITUDE + TEXT + " , "
			+ VERTICAL_ACCURACY + TEXT + " , "
			+ HORIZONTAL_ACCURACY + TEXT + " , "
			+ VERTICAL_FEET + TEXT + " , "
			+ RUNS + FLOAT
			+ " ) ";

	public void insertTrack(TrackDTO trackDTO) {
		SQLiteDatabase db = openDatabase();
		ContentValues cv = new ContentValues();
		cv.put(TIMESTAMP, trackDTO.getTimestamp());
		cv.put(SEGMENT_ID, trackDTO.getSegmentId());
		cv.put(TYPE, trackDTO.getType());
		cv.put(GEOMETRY_TYPE, trackDTO.getGeometryType());
		cv.put(LATITUDE, trackDTO.getLatitude());
		cv.put(LONGITUDE, trackDTO.getLongitude());
		cv.put(ALTITUDE, trackDTO.getAltitude());
		cv.put(VERTICAL_ACCURACY, trackDTO.getVerticalAccuracy());
		cv.put(HORIZONTAL_ACCURACY, trackDTO.getHorizontalAccuracy());
		cv.put(VERTICAL_FEET, trackDTO.getVerticalFeet());
		cv.put(RUNS, trackDTO.getRuns());
		db.insertWithOnConflict(TABLE_TRACKS, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
		closeDatabase();
	}

	public List<TrackDTO> getTracks() {
		SQLiteDatabase db = openDatabase();
		List<TrackDTO> list = new ArrayList<>();
		Cursor cursor = db.query(TABLE_TRACKS, null, null, null, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			do {
				TrackDTO object = getTrackDTO(cursor);
				list.add(object);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return list;
	}

	public List<TrackDTO> getTracksBySegmentId(int segmentId) {
		SQLiteDatabase db = openDatabase();
		List<TrackDTO> list = new ArrayList<>();
		Cursor cursor = db.query(TABLE_TRACKS, null, SEGMENT_ID + " = ?", new String[]{String.valueOf(segmentId)}, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			do {
				TrackDTO object = getTrackDTO(cursor);
				list.add(object);
			} while (cursor.moveToNext());
			cursor.close();
		}
		return list;
	}

	public TrackDTO getTrackDTO(Cursor cursor) {
		TrackDTO object = null;
		long timestamp = cursor.getLong(cursor.getColumnIndex(TIMESTAMP));
		String type = cursor.getString(cursor.getColumnIndex(TYPE));
		String geometryType = cursor.getString(cursor.getColumnIndex(GEOMETRY_TYPE));
		double latitude = cursor.getDouble(cursor.getColumnIndex(LATITUDE));
		double longitude = cursor.getDouble(cursor.getColumnIndex(LONGITUDE));
		double altitude = cursor.getDouble(cursor.getColumnIndex(ALTITUDE));
		int verticalAccuracy = cursor.getInt(cursor.getColumnIndex(VERTICAL_ACCURACY));
		int horizontalAccuracy = cursor.getInt(cursor.getColumnIndex(HORIZONTAL_ACCURACY));
		int verticalFeet = cursor.getInt(cursor.getColumnIndex(VERTICAL_FEET));
		float runs = cursor.getFloat(cursor.getColumnIndex(RUNS));
		int segmentId = cursor.getInt(cursor.getColumnIndex(SEGMENT_ID));
		object = new TrackDTO(timestamp, type, geometryType, latitude, longitude, altitude, verticalAccuracy, horizontalAccuracy, verticalFeet, runs, segmentId);
		return object;
	}

	public void removeAllTracks() {
		SQLiteDatabase db = openDatabase();
		db.execSQL("delete from " + TABLE_TRACKS);
		closeDatabase();
	}

	public float getTopSpeed() {
		float topSpeed = 0.0f;
		SQLiteDatabase db = openDatabase();
		Cursor cursor = db.query(TABLE_TRACKS, new String[]{"MAX(" + RUNS + ")"}, null, null, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			topSpeed = cursor.getInt(0);
		}
		return topSpeed;
	}

	public int getMaxSegmentPoint() {
		int setment = 0;
		SQLiteDatabase db = openDatabase();
		Cursor cursor = db.query(TABLE_TRACKS, new String[]{"MAX(" + SEGMENT_ID + ")"}, null, null, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			setment = cursor.getInt(0);
		}
		return setment;
	}

=========================================================================================

	//  Favourites functionality
=========================================================================================

private static final String TABLE_FAVORITES = "TABLE_FAVORITES";
	private static final String COLUMN_FAVORITES_ID = "COLUMN_FAVORITES_ID";
	private static final String COLUMN_FAVORITES_NAME = "COLUMN_FAVORITES_NAME";

	private static final String QUERY_CREATE_TABLE_FAVORITES = "CREATE TABLE " + TABLE_FAVORITES
			+ " ( "
			+ COLUMN_FAVORITES_ID + TEXT + PRIMARY_KEY + " , "
			+ COLUMN_FAVORITES_NAME + TEXT
			+ " ) ";

	public void insertFavouriteResort(ResortDetailDTO object) {
		SQLiteDatabase db = openDatabase();
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_FAVORITES_ID, object.getMountain().getId());
		cv.put(COLUMN_FAVORITES_NAME, object.getMountain().getName());
		db.insertWithOnConflict(TABLE_FAVORITES, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
		closeDatabase();
	}

	public void insertFavorites(List<Favourites> list) {
		try {
			if (null != list) {
				for (int i = 0; i < list.size(); i++) {
					String id = list.get(i).getMountainProfile().get_id();
					if (!resortAlreadyPresentInDB(id)) {
						SQLiteDatabase db = openDatabase();
						ContentValues cv = new ContentValues();
						cv.put(COLUMN_FAVORITES_ID, id);
						cv.put(COLUMN_FAVORITES_NAME, list.get(i).getName());
						db.insertWithOnConflict(TABLE_FAVORITES, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
						closeDatabase();
					}
				}
			}
		} catch (Exception e) {

		}
	}

	public void removeFavouriteResort(String id) {
		SQLiteDatabase db = openDatabase();
		db.delete(TABLE_FAVORITES, COLUMN_FAVORITES_ID + " = ?", new String[]{String.valueOf(id)});
		closeDatabase();
	}

	public boolean resortAlreadyPresentInDB(String id) {
		boolean flag = false;
		SQLiteDatabase db = openDatabase();
		Cursor cursor = db.query(TABLE_FAVORITES, null, COLUMN_FAVORITES_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			flag = true;
			cursor.close();
		}
		closeDatabase();
		return flag;
	}

	public void loadFavorites() {
		SQLiteDatabase db = openDatabase();
		Cursor cursor = db.query(TABLE_FAVORITES, null, null, null, null, null, null);
		if (null != cursor && cursor.moveToFirst()) {
			do {
				Logger.warn("ID: " + cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITES_ID)));
				Logger.warn("Name: " + cursor.getString(cursor.getColumnIndex(COLUMN_FAVORITES_NAME)));
			} while (cursor.moveToNext());
			cursor.close();
		}
	}


}
*/
