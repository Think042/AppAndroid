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
import com.example.budgetbuddyapp.databinding.UiEditExpenseBinding;
import java.util.ArrayList;

public class ExpenseEdit extends AppCompatActivity {

    UiEditExpenseBinding binding;
    int[] categoryImages;
    Boolean[] isOnly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UiEditExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        categoryImages = new int[]{R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        isOnly = new Boolean[]{true};

        Intent intent = getIntent();
        if (intent != null) {
            String expenseID = intent.getStringExtra("expenseID");
            // Mô phỏng lấy dữ liệu từ intent
            fetchExpenseDetails();
        }

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

        ArrayList<String> spinner_choice = new ArrayList<>();
        spinner_choice.add("Chỉ tháng này");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinner_choice);
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        binding.spinnerAddExpense.setAdapter(adapter);

        binding.closeButton.setOnClickListener(view -> onBackPressed());

        binding.saveButton.setOnClickListener(view -> {
            String expenseID = getIntent().getStringExtra("expenseID");
            if (TextUtils.isEmpty(binding.inputExpenseLimit.getText()) || !TextUtils.isDigitsOnly(binding.inputExpenseLimit.getText())) {
                Toast.makeText(ExpenseEdit.this, "Vui lòng nhập giới hạn chi tiêu, giới hạn chi tiêu là một số!", Toast.LENGTH_SHORT).show();
            } else {
                // Mô phỏng cập nhật dữ liệu
                updateExpenseDetails(expenseID);
            }
        });
    }

    private void fetchExpenseDetails() {
        // Giả lập dữ liệu mẫu
        binding.expenseName.setText("Chi tiêu hàng tháng");
        binding.expenseImage.setImageResource(categoryImages[0]);
        binding.inputExpenseLimit.setText("5000000");

        // Thiết lập số dư ví
        TextView balance = findViewById(R.id.balance);
        balance.setText("10,000,000 đ");
    }

    private void updateExpenseDetails(String expenseID) {
        // Lấy giá trị mới từ giao diện
        String updatedLimitText = binding.inputExpenseLimit.getText().toString();
        int updatedLimit = Integer.parseInt(updatedLimitText);

        // Hiển thị thông báo thành công
        Toast.makeText(ExpenseEdit.this, "Cập nhật giới hạn chi tiêu thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}