package edu.hul233psu.commonstock;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

/**
 * Created by edwardlai on 3/19/18.
 */

public class ResultDatabase extends SQLiteOpenHelper {

        interface OnDBReadyListener {
            void onDBReady(SQLiteDatabase theDB);
        }

        private static final int DATABASE_VERSION = 5;
        private static final String DATABASE_NAME = "result.db";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE result (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Titles TEXT, " +
                        "ValueIfUp INTEGER, " +
                        "ValueIfDown INTEGER," +
                        "Risk INTEGER," +
                        "ReturnValue INTEGER)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS result";

        private static ResultDatabase theDb;
        private Context appContext;

        //Good
        private ResultDatabase(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
            appContext = context.getApplicationContext();
        }

        //Good
        public static synchronized ResultDatabase getInstance(Context context) {
            if (theDb == null) {
                theDb = new ResultDatabase(context.getApplicationContext());
            }
            return theDb;
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL_CREATE_ENTRIES);

            // Note:  We start by populating the db with arrays
            String[] titles =
                    appContext.getResources().getStringArray(R.array.Titles);
            String[] valueIfUP =
                    appContext.getResources().getStringArray(R.array.ValueIfUP);
            String[] valueIfDown =
                    appContext.getResources().getStringArray(R.array.ValueIfDown);
            String[] risk =
                    appContext.getResources().getStringArray(R.array.Risk);
            String[] returnValue =
                    appContext.getResources().getStringArray(R.array.ReturnValue);


            // Q:  Why are we wrapping these operations in a transaction?
            db.beginTransaction();
            ContentValues values = new ContentValues();

            for (int i = 0; i < titles.length; i++) {
                values.put("Titles", titles[i]);
                values.put("ValueIfUp", valueIfUP[i]);
                values.put("ValueIfDown", valueIfDown[i]);
                values.put("Risk", risk[i]);
                values.put("ReturnValue",returnValue[i]);
                db.insert("result", null, values);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
        }



        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }

        @Override
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }

        public void getWritableDatabase(OnDBReadyListener listener) {
            new OpenDbAsyncTask().execute(listener);
        }

        private static class OpenDbAsyncTask extends AsyncTask<OnDBReadyListener,Void,SQLiteDatabase> {
            OnDBReadyListener listener;

            @Override
            protected SQLiteDatabase doInBackground(OnDBReadyListener... params){
                listener = params[0];
                return ResultDatabase.theDb.getWritableDatabase();
            }

            @Override
            protected void onPostExecute(SQLiteDatabase db) {
                //Make that callback
                listener.onDBReady(db);
            }
        }


}
