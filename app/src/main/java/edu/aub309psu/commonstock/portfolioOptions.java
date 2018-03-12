package edu.aub309psu.commonstock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.aub309psu.commonstock.R;

public class portfolioOptions extends AppCompatActivity {

    Button continuebutton;

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
                    //Code for this option
                }
                else {
                    //Code not for this option
                }
                break;
            case R.id.checkBox2:
                if (checked) {
                    //Code for this option
                }
                else {
                    //Code not for this option
                }
                break;
            case R.id.checkBox3:
                if (checked) {
                    //Code for this option
                }
                else {
                    //Code not for this option
                }
                break;
            case R.id.checkBox4:
                if (checked) {
                    //Code for this option
                }
                else {
                    //Code not for this option
                }
                break;
            case R.id.checkBox5:
                if (checked) {
                    //Code for this option
                }
                else {
                    //Code not for this option
                }
                break;
            default:
                //Code here
        }
    }


    public void goToUserInput(View view) {
        Intent intent = new Intent(this, UserInput.class);
        startActivity(intent);
    }

    private View.OnClickListener ContinueListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToUserInput(view); //Code that causes the AlertDialog to be displayed
        }
    };

}


