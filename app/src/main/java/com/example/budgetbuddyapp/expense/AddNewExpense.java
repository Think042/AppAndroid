package com.example.budgetbuddyapp.expense;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.databinding.UiAddExpenseBinding;
import java.util.ArrayList;
import java.util.Calendar;

public class AddNewExpense extends AppCompatActivity {

    UiAddExpenseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UiAddExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        final Boolean[] isOnly = {true};

        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill,
                // giữ nguyên mảng image
                R.drawable.c_gardening};

        Intent intent = getIntent();
        if (intent != null) {
            String expenseName = intent.getStringExtra("expenseName");
            int expenseImage = intent.getIntExtra("expenseImage", 0);

            // Cập nhật UI
            binding.expenseName.setText(expenseName);
            binding.expenseImage.setImageResource(categoryImages[expenseImage]);

            // Giả lập số dư ví
            TextView balance = findViewById(R.id.balance);
            balance.setText("10,000,000 đ");
        }

        ArrayList<String> spinner_choice = new ArrayList<>();
        spinner_choice.add("Chỉ tháng này");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_choice);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.spinnerAddExpense.setAdapter(adapter);

        binding.spinnerAddExpense.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String item = adapterView.getItemAtPosition(i).toString();
                if (item.equals("Chỉ tháng này")) {
                    isOnly[0] = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        binding.closeButton.setOnClickListener(view -> onBackPressed());

        binding.addNew.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.inputExpenseLimit.getText()) || !TextUtils.isDigitsOnly(binding.inputExpenseLimit.getText())) {
                Toast.makeText(AddNewExpense.this, "Vui lòng nhập giới hạn chi tiêu, giới hạn chi tiêu là một số!", Toast.LENGTH_SHORT).show();
            } else {
                // Xử lý dữ liệu và hiển thị thông báo
                Toast.makeText(AddNewExpense.this, "Tạo giới hạn chi tiêu thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}