package edu.hul233psu.commonstock;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Stock_Results extends AppCompatActivity {

    //This is what we have for the new additions to my projects for the AlertDialog, Toast, and UI button Wire-up.
    public static String saveTitle = "";
    private SQLiteDatabase db;
    Button emailresultbutton;
    Button done;

    TextView valueup;
    TextView valuedown;
    TextView valuerisk;
    TextView valuereturn;


    public static float valueifup;
    public static float valueifdown;
    public static float prisk;
    public static float preturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock__results);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        emailresultbutton= findViewById(R.id.emailresultsbutton);
        done = findViewById(R.id.buttondone);
        valueup = findViewById(R.id.Valueup);
        valuedown = findViewById(R.id.ValueDown);
        valuerisk = findViewById(R.id.ValueRisk);
        valuereturn = findViewById(R.id.ValueReturn);

        ResultDatabase.getInstance(this).getWritableDatabase(new ResultDatabase.OnDBReadyListener() {
            @Override
            public void onDBReady(SQLiteDatabase theDB) {
                db = theDB;
                //final long rowid = cursor.getLong(cursor.getColumnIndex(_id));
            }
        });

        calculateresults(); //Calculate Function


        emailresultbutton.setOnClickListener(emailButtonListener); //Wires the button to the UI Listener
        done.setOnClickListener(doneButtonListener); //Wires the button to the UI Listener
    }

    //Listener for the Email Button
    private View.OnClickListener emailButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Method that issues the Toast
            //Toast.makeText(Stock_Results.this, "Email Sent", Toast.LENGTH_SHORT).show();
            Intent intent_2 = new Intent(Stock_Results.this, EmailSendingPage.class);
            intent_2.putExtra("VIU", String.valueOf(valueup));
            intent_2.putExtra("VID", String.valueOf(valuedown));
            intent_2.putExtra("PRisk", String.valueOf(valuerisk));
            intent_2.putExtra("PRET", String.valueOf(valuereturn));
            startActivity(intent_2);
            //The toast appears when the user presses the Email Button and it succeeds.
        }
    };

    //Listener for the Done Button
    private View.OnClickListener doneButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            displaySave(view); //Code that causes the AlertDialog to be displayed
        }
    };

    private void calculateresults() {
        float stocksup;
        float stocksdown;
        float bonds;
        float futurecontractsup;
        float futurecontractsdown;
        float calloptionsup;
        float calloptionsdown;
        float putoptionsup;
        float putoptionsdown;

        float sinitvalue = UserInput.stockinitvalue;
        float binitvalue = UserInput.bondinitvalue;
        float finitvalue = UserInput.forwardcinitvalue;
        float cinitvalue = UserInput.callinitvalue;
        float pinitvalue = UserInput.putinitvalue;

        float snumber = UserInput.stocknumber;
        float bnumber = UserInput.bondnumber;
        float fnumber = UserInput.forwardcnumber;
        float cnumber = UserInput.callnumber;
        float pnumber = UserInput.putnumber;

        float sfuturelow = UserInput.stockfuturelow;
        float ffuturelow = UserInput.forwardcfuturelow;
        float cfuturelow = UserInput.callfuturelow;
        float pfuturelow = UserInput.putfuturelow;

        float sfuturehigh = UserInput.stockfuturehigh;
        float bfuture = UserInput.bondfuture;
        float ffuturehigh = UserInput.forwardcfuturehigh;
        float cfuturehigh = UserInput.callfuturehigh;
        float pfuturehigh = UserInput.putfuturehigh;
        float percentage = UserInput.percent;
        float riskpart1;
        float riskpart2;
        float percentup = percentage / 100;
        float percentdown = 1 - percentup;

        if (snumber == 0) { //To avoid divide by zero if field is empty
            stocksup = 0;
            stocksdown = 0;
        }
        else { //Calculate results otherwise
            stocksup = ((sfuturehigh * snumber) - (sinitvalue * snumber)) / (sinitvalue * snumber);
            stocksdown = ((sfuturelow * snumber) - (sinitvalue * snumber)) / (sinitvalue * snumber);
        }
        if (bnumber == 0) { //To avoid divide by zero if field is empty
            bonds = 0;
        }
        else { //Calculate results otherwise
            bonds = ((bfuture * bnumber) - (binitvalue * bnumber)) / (binitvalue * bnumber);
        }
        if (fnumber == 0) { //To avoid divide by zero if field is empty
            futurecontractsup = 0;
            futurecontractsdown = 0;
        }
        else { //Calculate results otherwise
            futurecontractsup = ((ffuturehigh * fnumber) - (finitvalue * fnumber)) / (finitvalue * fnumber);
            futurecontractsdown = ((ffuturelow * fnumber) - (finitvalue * fnumber)) / (finitvalue * fnumber);
        }
        if (cnumber == 0) { //To avoid divide by zero if field is empty
            calloptionsup = 0;
            calloptionsdown = 0;
        }
        else { //Calculate results otherwise
            calloptionsup = ((cfuturehigh * cnumber) - (cinitvalue * cnumber)) / (cinitvalue * cnumber);
            calloptionsdown = ((cfuturelow * cnumber) - (cinitvalue * cnumber)) / (cinitvalue * cnumber);
        }
        if (pnumber == 0) {  //To avoid divide by zero if field is empty
            putoptionsup = 0;
            putoptionsdown = 0;
        }
        else { //Calculate results otherwise
            putoptionsup = ((pfuturehigh * pnumber) - (pinitvalue * pnumber)) / (pinitvalue * pnumber);
            putoptionsdown = ((pfuturelow * pnumber) - (pinitvalue * pnumber)) / (pinitvalue * pnumber);
        }

        //Finishing calculations
        Stock_Results.valueifup = stocksup + bonds + futurecontractsup + calloptionsup + putoptionsup;
        Stock_Results.valueifdown = stocksdown + bonds + futurecontractsdown + calloptionsdown + putoptionsdown;
        Stock_Results.preturn = (Stock_Results.valueifup * percentup) + (Stock_Results.valueifdown * percentdown);
        riskpart1 = (percentup * ((Stock_Results.valueifup - Stock_Results.preturn) * (Stock_Results.valueifup - Stock_Results.preturn)));
        riskpart2 = (percentdown * ((Stock_Results.valueifdown - Stock_Results.preturn) * (Stock_Results.valueifdown - Stock_Results.preturn)));
        Stock_Results.prisk = (float) Math.sqrt(riskpart1 + riskpart2);

        //display the results
        valueup.setText(Float.toString(valueifup));
        valuedown.setText(Float.toString(valueifdown));
        valuerisk.setText(Float.toString(prisk));
        valuereturn.setText(Float.toString(preturn));

    }

    private void saveToDB (String title) { //Function to save the values to the database
        ContentValues values = new ContentValues();

        values.put("Titles", title);
        values.put("ValueIfUp", valueifup);
        values.put("ValueIfDown", valueifdown);
        values.put("Risk", prisk);
        values.put("ReturnValue", preturn);

        try {
            db.insert("result", null, values);
            finish();

        } catch (SQLException e) {
            Toast.makeText(Stock_Results.this,"Error Updating Database",Toast.LENGTH_LONG).show();
        }

    }

    public void displaySave(final View view){
        //Code that subclasses AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Save the data bafore finish?"); //Code to display the dialog
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) { //Code for the buttons
                        getSaveTitle(view);

                    }

                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @Override //Code for the other buttons
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Stock_Results.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void getSaveTitle(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Name the result");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveTitle = input.getText().toString();
                saveToDB(saveTitle); //Save to database

                Intent intent = new Intent(Stock_Results.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(Stock_Results.this,"Data Saved",Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

}