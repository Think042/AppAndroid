package com.example.budgetbuddyapp.Profile;

import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.budgetbuddyapp.R;

public class Editprofile extends AppCompatActivity {

    private EditText fullname, email;
    private ImageButton btn_yes1, btn_yes2;
    private ImageView backButton;
    private static final String PREFS_NAME = "ProfilePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile);

        fullname = findViewById(R.id.editTxt_username);
        btn_yes1 = findViewById(R.id.btn_yes1);
        backButton = findViewById(R.id.backButton);

        // Đọc thông tin profile từ SharedPreferences
        // hoặc dùng giá trị mặc định nếu chưa có
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedName = settings.getString("fullname", "Nguyễn Văn A");
        fullname.setText(savedName);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btn_yes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullname.getText().toString().isEmpty()) {
                    Toast.makeText(Editprofile.this, "Vui lòng nhập tên người dùng!", Toast.LENGTH_SHORT).show();
                } else {
                    String newFullname = fullname.getText().toString();

                    // Lưu thông tin mới vào SharedPreferences
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("fullname", newFullname);
                    editor.apply();

                    Toast.makeText(Editprofile.this, "Cập nhật tên người dùng thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}