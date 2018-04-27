package edu.hul233psu.commonstock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UserInfo extends AppCompatActivity {

    private EditText userInfoAdress;
    private EditText userPassword;
    private EditText userName;
    private Button saveUserInfo;
    public static final String MY_PREFS_NAME = "MyPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        userInfoAdress = (EditText) findViewById(R.id.userEmailAdress);
        userPassword = (EditText) findViewById(R.id.userPassword);
        userName = (EditText) findViewById(R.id.userName);
        saveUserInfo= (Button) findViewById(R.id.saveUserInfo);

        saveUserInfo.setOnClickListener(saveUserInfoListener);
        retrieve();


    }

    private View.OnClickListener saveUserInfoListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            save(view); //Code that causes the Saved Values activity to start
            Toast.makeText(UserInfo.this,"Data Saved",Toast.LENGTH_LONG).show();
        }
    };


    public void save(View view) {

        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString("userName", String.valueOf(userName.getText()));
        editor.putString("userAddress", String.valueOf(userInfoAdress.getText()));
        editor.putString("userPass", String.valueOf(userPassword.getText()));
        editor.apply();

        //Config.EMAIL= String.valueOf(userInfoAdress.getText());
        //Config.PASSWORD= String.valueOf(userPassword.getText());
    }
    public void retrieve(){
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPrefsFile", MODE_PRIVATE);
        String email=pref.getString("userAddress", null);
        String password=pref.getString("userPass", null);
        String name=pref.getString("userName", null);

        userName.setText(name);
        userInfoAdress.setText(email);
        userPassword.setText(password);



    }


}
