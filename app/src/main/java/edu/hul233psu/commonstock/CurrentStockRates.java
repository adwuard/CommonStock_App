package edu.hul233psu.commonstock;

import android.os.Bundle;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;

import org.json.JSONObject;
import org.json.JSONException;
import org.json.JSONArray;

public class CurrentStockRates extends AppCompatActivity {

    Button equityButton;
    EditText eqname;
    TextView stockname;
    TextView stockdate;
    TextView stockopen;
    TextView stockhi;
    TextView stocklo;
    TextView stockvol;
    String EQname = "";

    static final String SERVER = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=";
    static final String TAG = "CurrentStockRates";

    AsyncHttpRequest asyncHttpRequest = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_stock_rates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        equityButton = findViewById(R.id.EquityButton);
        eqname = findViewById(R.id.EquityEditText);
        stockname = findViewById(R.id.StockName);
        stockdate = findViewById(R.id.StockDate);
        stockopen = findViewById(R.id.OpenValue);
        stockhi = findViewById(R.id.HighValue);
        stocklo = findViewById(R.id.LowValue);
        stockvol = findViewById(R.id.Volume);

        equityButton.setOnClickListener(equityListener);

    }

    private View.OnClickListener equityListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            EQname = eqname.getText().toString();
            if (asyncHttpRequest != null) {
                asyncHttpRequest.cancel(true);
            }
            asyncHttpRequest = new AsyncHttpRequest("GET" , SERVER + EQname +
                    "&apikey=ETLICFJMW6Q512HN" , null, null);
            asyncHttpRequest.execute();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (asyncHttpRequest != null) {
            asyncHttpRequest.cancel(true);
        }
    }

    class AsyncHttpRequest extends AsyncTask<Void,Void,JSONObject> {
        String method;
        String url;
        JSONViaHttp.QueryStringParams queryStringParams;
        String payload;
        public AsyncHttpRequest(String method, String url,
                                JSONViaHttp.QueryStringParams queryStringParams, String payload) {
            this.method = method;
            this.url = url;
            this.queryStringParams = queryStringParams;
            this.payload = payload;
        }
        @Override
        protected JSONObject doInBackground(Void... params) {
            return JSONViaHttp.get(method, url, queryStringParams, payload);
        }
        @Override
        protected void onPostExecute(JSONObject result) {
            super.onPostExecute(result);
            if (result != null) {
                try {
                    JSONObject metadata = result.getJSONObject("Meta Data");

                    String sname = metadata.getString("2. Symbol");
                    String sdate = metadata.getString("3. Last Refreshed");

                    JSONObject timeSeriesJson = result.getJSONObject("Time Series (Daily)");
                    JSONObject dailyObject = timeSeriesJson.getJSONObject(sdate);

                    String opPrice = dailyObject.getString("1. open");
                    String hiPrice = dailyObject.getString("2. high");
                    String loPrice = dailyObject.getString("3. low");
                    String svol = dailyObject.getString("5. volume");

                    stockname.setText(sname);
                    stockdate.setText(sdate);
                    stockopen.setText(opPrice);
                    stockhi.setText(hiPrice);
                    stocklo.setText(loPrice);
                    stockvol.setText(svol);

                }
                catch (JSONException e) {
                    Log.e(TAG, "Error retrieving list: " + e.getMessage());
                }
            }
            else {
                stockname.setText("Not Found");
                stockdate.setText("Not Found");
                stockopen.setText("Not Found");
                stockhi.setText("Not Found");
                stocklo.setText("Not Found");
                stockvol.setText("Not Found");
            }
            asyncHttpRequest = null;
        }
    }
}


