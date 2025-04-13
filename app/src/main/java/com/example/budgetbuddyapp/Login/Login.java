package com.example.budgetbuddyapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import com.example.budgetbuddyapp.Navigation;
import com.example.budgetbuddyapp.R;

public class Login extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private TextView signupRedirectText, txtview_incorrect, txt_forgotpassword;
    private Button loginButton;
    private CheckBox checkbox;
    public static final String SHARED_PREFS = "sharePrefs";

    // Demo credentials for testing
    private static final String DEMO_EMAIL = "user@example.com";
    private static final String DEMO_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.btn_login);
        signupRedirectText = findViewById(R.id.register_redirect);
        checkbox = findViewById(R.id.check_save);
        txtview_incorrect = findViewById(R.id.incorrect);
        txt_forgotpassword = findViewById(R.id.forgot_password);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();
                String pass = loginPassword.getText().toString();

                if(!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if (!pass.isEmpty()) {
                        // Mock authentication logic
                        if(email.equals(DEMO_EMAIL) && pass.equals(DEMO_PASSWORD) ||
                                email.equals("test@gmail.com") && pass.equals("123456")) {

                            // Lưu đăng nhập nếu checkbox được chọn
                            if (checkbox.isChecked()) {
                                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("name", "true");
                                editor.apply();
                            }

                            Toast.makeText(Login.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this, Navigation.class));
                            finish();
                        } else {
                            txtview_incorrect.setVisibility(View.VISIBLE);
                            Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        loginPassword.setError("Mật khẩu không thể trống");
                    }
                } else if (email.isEmpty()) {
                    loginEmail.setError("Email không thể trống!");
                } else {
                    loginEmail.setError("Email không tồn tại!");
                }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        txt_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, ForgotPassword.class));
            }
        });
    }
}