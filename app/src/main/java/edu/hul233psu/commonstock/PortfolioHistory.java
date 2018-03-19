package edu.hul233psu.commonstock;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class PortfolioHistory extends AppCompatActivity {

    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;
    private final static int ADD_ACTIVITY_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        onCreateSetupJokeListAdapter();
        onCreateSetupJokeListView();

        ResultDatabase.getInstance(this).getWritableDatabase(new ResultDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase theDB) {
                db = theDB;
                dbAsyncLoadCursor(false);
            }
        });

    }



    private void onCreateSetupJokeListAdapter() {
        // Initially set cursor to null BC IT IS NOT YET READY!
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, null,
                new String[]{"Titles"},
                new int[]{R.id.txtTitle}, 0);
    }



    //Adapt the list view into the list view
    private void onCreateSetupJokeListView() {
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                displayJoke(id);// call the joke
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position,
                                           long id) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("rowid", id);
                startActivityForResult(intent,ADD_ACTIVITY_RESULT);
                return true;
            }
        });*/
    }

/*
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_RESULT  && resultCode != AddActivity.RESULT_DB_UNCHANGED) {
            dbAsyncLoadCursor(resultCode == AddActivity.RESULT_DB_ADDED_RECORD);
        }
    }
*/


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }




    @SuppressLint("StaticFieldLeak")
    private void dbAsyncLoadCursor(boolean scrollToEnd) {

        new AsyncTask<Boolean, Void, Cursor>() {//error
            boolean scrollToEnd;
            @Override
            protected Cursor doInBackground(Boolean... params) {
                scrollToEnd = params[0];
                String where = null;
                String[] projection = {"_id", "Titles"};
                return db.query("result", projection,where,null, null, null, null);//errorr
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                adapter.swapCursor(cursor);
                if (scrollToEnd)
                    ((ListView) findViewById(R.id.listView)).setSelection(adapter.getCount() - 1);
            }
        }.execute(scrollToEnd);
    }


/*

    private void displayJoke(long rowid) {
        String where = "_id = " + rowid;

        String[] projection = {"_id", "title", "setup", "punchline", "liked"};
        Cursor cursor = db.query("jokes", projection, where,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            // Arguments are a way to set values for the dialog that will be passed
            // even if fragment gets destroyed and recreated
            Bundle args = new Bundle();
            args.putLong("rowid", rowid);
            args.putString("title",cursor.getString(cursor.getColumnIndexOrThrow("title")));
            args.putString("setup",cursor.getString(cursor.getColumnIndexOrThrow("setup")));
            args.putString("punchline",cursor.getString(cursor.getColumnIndexOrThrow("punchline")));

            DisplaySetupDialog setupDialog = new DisplaySetupDialog();
            setupDialog.setArguments(args);
            setupDialog.show(getFragmentManager(), "setupDialog");
        }
        else {
            Toast.makeText(MainActivity.this, "Record could not be retrieved...", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }



    private void update(long rowid, String liked) {
        ContentValues values = new ContentValues();
        values.put("liked", liked);
        String where = "_id = " + rowid;

        int count = 0;
        try {
            count = db.update("jokes", values, where, null);
        } catch (SQLException e) {
            Log.e("JokeDB", e.getMessage());
        }
        if (count == 0) {
            Toast.makeText(MainActivity.this, "Error updating record.", Toast.LENGTH_LONG).show();
        } else {
            dbAsyncLoadCursor(false);
        }
    }



    public static class DisplaySetupDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final long rowid = getArguments().getLong("rowid");
            final String title = getArguments().getString("title");
            final String setup = getArguments().getString("setup");
            final String punchline = getArguments().getString("punchline");

            builder.setTitle(title)
                    .setMessage(setup)
                    .setPositiveButton("Punchline",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    DisplayPunchlineDialog punchlineDialog =
                                            new DisplayPunchlineDialog();

                                    Bundle args = new Bundle();
                                    args.putLong("rowid", rowid);
                                    args.putString("title", title);
                                    args.putString("punchline", punchline);
                                    punchlineDialog.setArguments(args);

                                    punchlineDialog.show(getFragmentManager(),
                                            "punchlineDialog");
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

            return builder.create();
        }
    }





    public static class DisplayPunchlineDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final long rowid = getArguments().getLong("rowid");
            String title = getArguments().getString("title");
            String punchline = getArguments().getString("punchline");

            builder.setTitle(title)
                    .setMessage(punchline)
                    .setPositiveButton("Like", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ((MainActivity) getActivity()).update(rowid, "Y");
                        }
                    })
                    .setNegativeButton("Dislike",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    ((MainActivity) getActivity()).update(rowid, "N");
                                }
                            })
                    .setNeutralButton("Decide later",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                }
                            });

            return builder.create();
        }
    }

*/
}
