package edu.hul233psu.commonstock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class UserInput extends AppCompatActivity {
    Button calculatebutton;
<<<<<<< HEAD
=======
    EditText stock1;
    EditText stock2;
    EditText stock3;
    EditText stock4;

    EditText bond1;
    EditText bond2;
    EditText bond3;

    EditText forwardc1;
    EditText forwardc2;
    EditText forwardc3;
    EditText forwardc4;

    EditText call1;
    EditText call2;
    EditText call3;
    EditText call4;

    EditText put1;
    EditText put2;
    EditText put3;
    EditText put4;


    EditText percentinput;

    public static float stockinitvalue;
    public static float bondinitvalue;
    public static float forwardcinitvalue;
    public static float callinitvalue;
    public static float putinitvalue;

    public static float stocknumber;
    public static float bondnumber;
    public static float forwardcnumber;
    public static float callnumber;
    public static float putnumber;

    public static float stockfuturelow;
    public static float forwardcfuturelow;
    public static float callfuturelow;
    public static float putfuturelow;

    public static float stockfuturehigh;
    public static float bondfuture;
    public static float forwardcfuturehigh;
    public static float callfuturehigh;
    public static float putfuturehigh;

    public static float percent;

>>>>>>> a0244a8

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calculatebutton= findViewById(R.id.calculate);

        calculatebutton.setOnClickListener(CalculateListener); //Wires the button to the UI Listener

    }

    public void goToStockResults(View view) {
        Intent intent = new Intent(this, Stock_Results.class);
        startActivity(intent);
    }

    private View.OnClickListener CalculateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToStockResults(view); //Code that causes the AlertDialog to be displayed
        }
    };

}
