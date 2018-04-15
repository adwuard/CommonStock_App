package edu.hul233psu.commonstock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserInput extends AppCompatActivity {

    Button calculatebutton;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        calculatebutton= findViewById(R.id.calculate);
        stock1 = findViewById(R.id.editText32);
        stock2 = findViewById(R.id.editText30);
        stock3 = findViewById(R.id.editText31);
        stock4 = findViewById(R.id.editText29);

        bond1 = findViewById(R.id.editText36);
        bond2 = findViewById(R.id.editText35);
        bond3 = findViewById(R.id.editText34);

        forwardc1 = findViewById(R.id.editText44);
        forwardc2 = findViewById(R.id.editText42);
        forwardc3 = findViewById(R.id.editText41);
        forwardc4 = findViewById(R.id.editText43);

        call1 = findViewById(R.id.editText48);
        call2 = findViewById(R.id.editText46);
        call3 = findViewById(R.id.editText47);
        call4 = findViewById(R.id.editText45);

        put1 = findViewById(R.id.editText6);
        put2 = findViewById(R.id.editText3);
        put3 = findViewById(R.id.editText);
        put4 = findViewById(R.id.editText4);
        percentinput = findViewById(R.id.editText49);

        stocknumber = Integer.parseInt(stock1.getText().toString());
        bondnumber = Integer.parseInt(bond1.getText().toString());
        forwardcnumber = Integer.parseInt(forwardc1.getText().toString());
        callnumber = Integer.parseInt(call1.getText().toString());
        putnumber = Integer.parseInt(put1.getText().toString());

        stockinitvalue = Integer.parseInt(stock2.getText().toString());
        bondinitvalue = Integer.parseInt(bond2.getText().toString());
        forwardcinitvalue = Integer.parseInt(forwardc2.getText().toString());
        callinitvalue = Integer.parseInt(call2.getText().toString());
        putinitvalue = Integer.parseInt(put2.getText().toString());

        stockfuturelow = Integer.parseInt(stock3.getText().toString());
        forwardcfuturelow = Integer.parseInt(forwardc3.getText().toString());
        callfuturelow = Integer.parseInt(call3.getText().toString());
        putfuturelow = Integer.parseInt(put3.getText().toString());

        stockfuturehigh = Integer.parseInt(stock4.getText().toString());
        bondfuture = Integer.parseInt(bond3.getText().toString());
        forwardcfuturehigh = Integer.parseInt(forwardc4.getText().toString());
        callfuturehigh = Integer.parseInt(call4.getText().toString());
        putfuturehigh = Integer.parseInt(put4.getText().toString());
        percent = Integer.parseInt(percentinput.getText().toString());

        calculatebutton.setOnClickListener(CalculateListener); //Wires the button to the UI Listener

    }

    public void updateUI(){
        if(portfolioOptions.isstocks==false)
        {
            stock1.setEnabled(false);
            stock2.setEnabled(false);
            stock3.setEnabled(false);
            stock4.setEnabled(false);
        }
        else {
            stock1.setEnabled(true);
            stock2.setEnabled(true);
            stock3.setEnabled(true);
            stock4.setEnabled(true);
        }
        if(portfolioOptions.isbonds==false)
        {
            bond1.setEnabled(false);
            bond2.setEnabled(false);
            bond3.setEnabled(false);
        }
        else {
            bond1.setEnabled(true);
            bond2.setEnabled(true);
            bond3.setEnabled(true);
        }
        if(portfolioOptions.isforwardcontract==false) {
            forwardc1.setEnabled(false);
            forwardc2.setEnabled(false);
            forwardc3.setEnabled(false);
            forwardc4.setEnabled(false);
        }
        else {
            forwardc1.setEnabled(true);
            forwardc2.setEnabled(true);
            forwardc3.setEnabled(true);
            forwardc4.setEnabled(true);
        }
        if(portfolioOptions.iscall==false) {
            call1.setEnabled(false);
            call2.setEnabled(false);
            call3.setEnabled(false);
            call4.setEnabled(false);
        }
        else {
            call1.setEnabled(true);
            call2.setEnabled(true);
            call3.setEnabled(true);
            call4.setEnabled(true);
        }
        if(portfolioOptions.isput==false) {
            put1.setEnabled(false);
            put2.setEnabled(false);
            put3.setEnabled(false);
            put4.setEnabled(false);
        }
        else {
            put1.setEnabled(true);
            put2.setEnabled(true);
            put3.setEnabled(true);
            put4.setEnabled(true);
        }


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