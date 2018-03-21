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
