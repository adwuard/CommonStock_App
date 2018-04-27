package edu.hul233psu.commonstock;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity {

    ImageButton CPortfolioButton;
    ImageButton StockRateButton;
    ImageButton SavedValuesButton;
    ImageButton SPButton;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SPButton = findViewById(R.id.imageButton4);
        CPortfolioButton=findViewById(R.id.imageButton);
        StockRateButton=findViewById(R.id.imageButton2);
        SavedValuesButton=findViewById(R.id.imageButton3);

        SPButton.setOnClickListener(sharePButtonListener);
        CPortfolioButton.setOnClickListener(CPortfolioListener); //Wires the button to the UI Listener
        StockRateButton.setOnClickListener(StockRateListener); //Wires the button to the UI Listener
        SavedValuesButton.setOnClickListener(SavedValueListener); //Wires the button to the UI Listener
    }


    private View.OnClickListener CPortfolioListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToCalc(view); //Code that causes the Portfolio Calculation Activity to start
        }
    };

    private View.OnClickListener StockRateListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToCurrStockRates(view); //Code that causes the Current Stock Rates Activity to start
        }
    };
    private View.OnClickListener sharePButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToSP(view); //Code that causes the Current Stock Rates Activity to start
        }
    };

    private View.OnClickListener SavedValueListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            goToPortfolioHistory(view); //Code that causes the Saved Values activity to start
        }
    };


    public void goToCalc(View view) {
        Intent intent = new Intent(this, portfolioOptions.class);
        startActivity(intent);
    }
    public void goToSP(View view) {
        Intent intent = new Intent(this, SharedPref.class);
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
