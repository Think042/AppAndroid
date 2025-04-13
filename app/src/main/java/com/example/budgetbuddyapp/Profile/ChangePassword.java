package com.example.budgetbuddyapp.Profile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.example.budgetbuddyapp.Navigation;
import com.example.budgetbuddyapp.R;

public class ChangePassword extends AppCompatActivity {

    private EditText editTxt_password1, editTxt_password2;
    private Button btn_continue;
    private ImageButton btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);

        editTxt_password1 = findViewById(R.id.editTxt_password1);
        editTxt_password2 = findViewById(R.id.editTxt_password2);
        btn_continue = findViewById(R.id.btn_continue2);
        btn_back = findViewById(R.id.btn_backto_profile2);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password1 = editTxt_password1.getText().toString();
                String password2 = editTxt_password2.getText().toString();

                if (password1.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(ChangePassword.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password1.equals(password2)) {
                    Toast.makeText(ChangePassword.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Mô phỏng đổi mật khẩu thành công
                Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                Intent refreshIntent = new Intent(ChangePassword.this, Navigation.class);
                refreshIntent.putExtra("selectedTab", 3);
                startActivity(refreshIntent);
                finish();
            }
        });
    }
}