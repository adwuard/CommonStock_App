package edu.hul233psu.commonstock;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class Stock_Results extends AppCompatActivity {

    //This is what we have for the new additions to my projects for the AlertDialog, Toast, and UI button Wire-up.

    Button emailresultbutton;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock__results);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        emailresultbutton= findViewById(R.id.emailresultsbutton);
        done = findViewById(R.id.buttondone);

        emailresultbutton.setOnClickListener(emailButtonListener); //Wires the button to the UI Listener
        done.setOnClickListener(doneButtonListener); //Wires the button to the UI Listener
    }

    //Listener for the Email Button
    private View.OnClickListener emailButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Method that issues the Toast
            Toast.makeText(Stock_Results.this, "Email Sent", Toast.LENGTH_SHORT).show();
            //The toast appears when the user presses the Email Button and it succeeds.
        }
    };

    //Listener for the Done Button
    private View.OnClickListener doneButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            open(view); //Code that causes the AlertDialog to be displayed
        }
    };

    public void open(View view){
        //Code that subclasses AlertDialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Save the data bafore finish?"); //Code to display the dialog
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) { //Code for the buttons
                        Toast.makeText(Stock_Results.this,"Data Saved",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Stock_Results.this, MainActivity.class);
                        startActivity(intent);
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
}
