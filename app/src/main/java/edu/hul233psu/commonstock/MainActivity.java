package edu.hul233psu.commonstock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void goToCalc(View view) {
        Intent intent = new Intent(this, portfolioOptions.class);
        startActivity(intent);
    }

    public void goToPortfolioHistory(View view) {
        Intent intent = new Intent(this, PortfolioHistory.class);
        startActivity(intent);
    }

    public void goToCurrStockRates(View view) {
        Intent intent = new Intent(this, CurrentStockRates.class);
        startActivity(intent);
    }




}
