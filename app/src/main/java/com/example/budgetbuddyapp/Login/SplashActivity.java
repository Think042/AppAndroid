package com.example.budgetbuddyapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.example.budgetbuddyapp.Navigation;
import com.example.budgetbuddyapp.R;

public class SplashActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                nextActivity();
            }
        }, 2000);
    }

    // Kiểm tra trạng thái đăng nhập được lưu trong SharedPreferences
    private void nextActivity() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        String check = sharedPreferences.getString("name", "");

        if (check.equals("true")) {
            // Người dùng đã đăng nhập trước đó
            Intent intent = new Intent(SplashActivity.this, Navigation.class);
            startActivity(intent);
        } else {
            // Chưa đăng nhập
            Intent intent = new Intent(SplashActivity.this, Login.class);
            startActivity(intent);
        }
        finish();
    }
}