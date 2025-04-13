package com.example.budgetbuddyapp.expense;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.databinding.UiExpenseEditScreenBinding;
import java.util.Calendar;

public class ExpenseEditScreen extends AppCompatActivity {

    UiExpenseEditScreenBinding binding;
    String expenseID;
    int expenseCurrent;

    @Override
    protected void onResume() {
        super.onResume();
        // Mô phỏng cập nhật dữ liệu khi quay lại màn hình
        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        // Cập nhật giao diện
        Intent intent = getIntent();
        if (intent != null) {
            int expenseLimit = intent.getIntExtra("expenseLimit", 5000000);
            binding.expenseLimitRemaining.setText(String.format("%,d", expenseLimit));
            binding.expenseLimit.setText(String.format("%,d", expenseLimit) + " đ");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UiExpenseEditScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        Intent intent = getIntent();
        if (intent != null) {
            expenseID = intent.getStringExtra("expenseID");
            String expenseName = intent.getStringExtra("expenseName");
            String expenseTime = intent.getStringExtra("expenseTime");
            int expenseImage = intent.getIntExtra("expenseImage", 0);
            int expenseLimit = intent.getIntExtra("expenseLimit", 5000000);
            expenseCurrent = intent.getIntExtra("expenseCurrent", 3200000);

            if (expenseTime == null) {
                expenseTime = "Chỉ tháng này";
            }

            if (!expenseTime.equals("Tất cả các tháng")) {
                // Lấy ngày hiện tại
                Calendar calendar = Calendar.getInstance();
                int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
                int remainingDays = lastDayOfMonth - currentDay;
                binding.expenseTimeRemaining.setText("Còn lại " + remainingDays + " ngày");
            } else {
                binding.expenseTimeRemaining.setText("");
            }

            // Cập nhật UI
            binding.expenseName.setText(expenseName);
            binding.expenseTime.setText(expenseTime);
            binding.expenseImage.setImageResource(categoryImages[expenseImage]);

            ProgressBar progressBar = binding.progressBar;
            int CurrentProgress = (int) ((float) expenseCurrent / expenseLimit * 100);

            if (CurrentProgress >= 100) {
                CurrentProgress = 100;
                progressBar.getProgressDrawable().setColorFilter(
                        Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                binding.expenseCurrent.setTextColor(Color.RED);
                binding.reachLimit.setVisibility(View.VISIBLE);
            } else {
                binding.expenseCurrent.setTextColor(Color.BLACK);
                binding.reachLimit.setVisibility(View.GONE);
            }

            progressBar.setProgress(CurrentProgress);

            binding.expenseLimit.setText(String.format("%,d", expenseLimit) + " đ");
            binding.expenseCurrent.setText(String.format("%,d", expenseCurrent));
            binding.expenseLimitRemaining.setText(String.format("%,d", expenseLimit));
        }

        binding.closeButton.setOnClickListener(view -> onBackPressed());

        binding.deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog());

        binding.editButton.setOnClickListener(view -> {
            Intent editIntent = new Intent(ExpenseEditScreen.this, ExpenseEdit.class);
            editIntent.putExtra("expenseID", getIntent().getStringExtra("expenseID"));
            startActivity(editIntent);
        });
    }

    private void deleteExpense() {
        // Mô phỏng xóa mục chi tiêu
        Toast.makeText(this, "Đã xóa giới hạn chi tiêu", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteConfirmationDialog() {
        // Tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ui_delete_expense, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(view -> {
            deleteExpense();
            dialog.dismiss();
        });

        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }
}