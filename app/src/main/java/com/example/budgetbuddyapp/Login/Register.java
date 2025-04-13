package com.example.budgetbuddyapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.example.budgetbuddyapp.R;

public class Register extends AppCompatActivity {

    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    private EditText register_fullname, retype_password;
    private ImageButton backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupEmail = findViewById(R.id.register_email);
        signupPassword = findViewById(R.id.register_password);
        signupButton = findViewById(R.id.btn_register);
        loginRedirectText = findViewById(R.id.login_redirect);
        register_fullname = findViewById(R.id.register_fullname);
        backButton = findViewById(R.id.btn_backtologin);
        retype_password = findViewById(R.id.register_retype_password);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String retype_pass = retype_password.getText().toString().trim();
                String fullname = register_fullname.getText().toString();

                if (user.isEmpty()){
                    signupEmail.setError("Email không thể trống!");
                    return;
                }
                if(pass.isEmpty()){
                    signupPassword.setError("Mật khẩu không thể trống!");
                    return;
                }
                if (!pass.equals(retype_pass)) {
                    signupPassword.setError("Mật khẩu không trùng nhau!");
                    retype_password.setError("Mật khẩu không trùng nhau!");
                    return;
                }

                // Mô phỏng đăng ký thành công
                Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}