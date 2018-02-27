package edu.hul233psu.commonstock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class portfolioOptions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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

    }


