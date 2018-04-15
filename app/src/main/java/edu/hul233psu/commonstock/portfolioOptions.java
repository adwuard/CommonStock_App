package edu.hul233psu.commonstock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

public class portfolioOptions extends AppCompatActivity {

    Button continuebutton;
    public static boolean isstocks;
    public static boolean isbonds;
    public static boolean isforwardcontract;
    public static boolean iscall;
    public static boolean isput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        continuebutton=findViewById(R.id.button4);
        continuebutton.setOnClickListener(ContinueListener); //Wires the button to the UI Listener
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.checkBox:
                if (checked) {
                    isstocks = true;
                }
                else {
                    isstocks = false;
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    isbonds = true;
                }
                else {
                    isbonds = false;
                }
                break;
            case R.id.checkBox3:
                if (checked) {
                    isforwardcontract = true;
                }
                else {
                    isforwardcontract = false;
                }
                break;
            case R.id.checkBox4:
                if (checked) {
                    iscall = true;
                }
                else {
                    iscall = false;
                }
                break;
            case R.id.checkBox5:
                if (checked) {
                    isput = true;
                }
                else {
                    isput = false;
                }
                break;
            default:
                isstocks = false;
                isbonds = false;
                isforwardcontract = false;
                iscall = false;
                isput = false;
        }
    }


    public void goToUserInput(View view) {
        Intent intent = new Intent(this, UserInput.class);
        startActivity(intent);
    }

    private View.OnClickListener ContinueListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!isstocks && !isbonds && !isforwardcontract && !iscall && !isput) { //Makes it mandatory to select at least one option.
                Toast.makeText(portfolioOptions.this, "Select at least one option from the list to proceed", Toast.LENGTH_SHORT).show();
            }
            else {
                goToUserInput(view); //Go to the User Input Activity
            }
        }
    };

}