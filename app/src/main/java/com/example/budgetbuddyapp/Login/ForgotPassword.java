package com.example.budgetbuddyapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.budgetbuddyapp.R;

public class ForgotPassword extends AppCompatActivity {

    private Button btn_countinue;
    private ImageButton btn_backtologin;
    private TextView txtview_loginemail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        btn_countinue = findViewById(R.id.btn_countinue);
        btn_backtologin = findViewById(R.id.btn_backtologin2);
        txtview_loginemail = findViewById(R.id.login_email2);

        btn_countinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtview_loginemail.getText().toString().trim();

                if (email.isEmpty()) {
                    txtview_loginemail.setError("Vui lòng nhập email");
                    return;
                }

                // Mô phỏng gửi email đặt lại mật khẩu thành công
                Intent intent = new Intent(ForgotPassword.this, ForgotPasswordSuccessfully.class);
                startActivity(intent);
            }
        });

        btn_backtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}