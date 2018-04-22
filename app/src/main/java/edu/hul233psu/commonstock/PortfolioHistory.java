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
    public static long rowid_temp;
    private SQLiteDatabase db;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        onCreateSetupListAdapter();
        onCreateSetupListView();

        ResultDatabase.getInstance(this).getWritableDatabase(new ResultDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase theDB) {
                db = theDB;
                dbAsyncLoadCursor(false);
                adapter.notifyDataSetChanged();
            }
        });
    }


    private void onCreateSetupListAdapter() {
        // Initially set cursor to null BC IT IS NOT YET READY!
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, null,
                new String[]{"Titles","ReturnValue"},
                new int[]{R.id.txtTitle, R.id.txtReturn}, 0);
    }



    //Adapt the list view into the list view
    private void onCreateSetupListView() {
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayInfo(id);
                rowid_temp=id;
            }
        });
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    //Will add in the add database after the main calculation is in good shape and functional



    @SuppressLint("StaticFieldLeak")
    private void dbAsyncLoadCursor(boolean scrollToEnd) {

        new AsyncTask<Boolean, Void, Cursor>() {
            boolean scrollToEnd;
            @Override
            protected Cursor doInBackground(Boolean... params) {
                scrollToEnd = params[0];
                String[] projection = {"_id", "Titles", "ReturnValue"};
                return db.query("result", projection,null,null, null, null, null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                adapter.swapCursor(cursor);
                if (scrollToEnd)
                    ((ListView) findViewById(R.id.listView)).setSelection(adapter.getCount() - 1);
            }
        }.execute(scrollToEnd);
    }





    private void displayInfo(long rowid) {
        String where = "_id = " + rowid;

        String[] projection = {"_id", "Titles", "ValueIfUp", "ValueIfDown", "Risk","ReturnValue"};

        Cursor cursor = db.query("result", projection, where,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            // Arguments are a way to set values for the dialog that will be passed
            // even if fragment gets destroyed and recreated
            Bundle args = new Bundle();
            args.putLong("rowid", rowid);
            args.putString("Titles",cursor.getString(cursor.getColumnIndexOrThrow("Titles")));
            args.putString("ValueIfUp",cursor.getString(cursor.getColumnIndexOrThrow("ValueIfUp")));
            args.putString("ValueIfDown",cursor.getString(cursor.getColumnIndexOrThrow("ValueIfDown")));
            args.putString("Risk",cursor.getString(cursor.getColumnIndexOrThrow("Risk")));
            args.putString("ReturnValue",cursor.getString(cursor.getColumnIndexOrThrow("ReturnValue")));



            DisplaySetupDialog setupDialog = new DisplaySetupDialog();
            setupDialog.setArguments(args);
            setupDialog.show(getFragmentManager(), "setupDialog");
        }
        else {
            Toast.makeText(this, "Record could not be retrieved...", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
    }


    public static class DisplaySetupDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final long rowid = getArguments().getLong("rowid");
            final String title = getArguments().getString("Titles");
            final String valueIfUp = getArguments().getString("ValueIfUp");
            final String valueIfDown = getArguments().getString("ValueIfDown");
            final String risk = getArguments().getString("Risk");
            final String returnValue = getArguments().getString("ReturnValue");

            builder.setTitle(title)
                    .setMessage(
                            "Id:"+rowid+"\n"+
                            "Value Up: "+ valueIfUp+"\n"+
                            "Value Down: "+ valueIfDown+"\n"+
                            "Risk: "+risk+"\n"+
                                    "ReturnValue: "+returnValue+"\n"
                    )
                    .setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            })

                    .setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ConfirmDeleteDialog confirmDialog = new ConfirmDeleteDialog();
                            confirmDialog.show(getFragmentManager(), "deletionConfirmation");

                        }
                    });
            return builder.create();
        }
    }

    public void deleteRecord(long rowid) {
        String where = "_id = " + rowid;
        db.delete("result", where, null);
    }

    public static class ConfirmDeleteDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setTitle("Delete this result?")
                    .setMessage("You will not be able to undo the deletion!")
                    .setPositiveButton("Delete",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    try {
                                        ((PortfolioHistory) getActivity()).deleteRecord(rowid_temp);
                                        getActivity().finish();
                                    } catch (SQLException e) {
                                        Toast.makeText(getActivity(),
                                                "Error deleting record.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton("Return to history",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dismiss();
                                }
                            });
            return builder.create();
        }
    }
    
}
