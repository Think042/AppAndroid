package com.example.budgetbuddyapp.goal;

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
import com.example.budgetbuddyapp.databinding.UiGoalFundBinding;

public class GoalFund extends AppCompatActivity {

    UiGoalFundBinding binding;
    String goalID;

    @Override
    protected void onResume() {
        super.onResume();
        // Mô phỏng cập nhật dữ liệu khi quay lại màn hình
        updateUIWithMockData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UiGoalFundBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        Intent intent = getIntent();
        if (intent != null) {
            goalID = intent.getStringExtra("goalID");
            String goalName = intent.getStringExtra("goalName");
            String date = intent.getStringExtra("date");
            int goalImage = intent.getIntExtra("goalImage", 0);
            Long goalCurrent = intent.getLongExtra("goalCurrent", 0);
            Long goalNumber = intent.getLongExtra("goalNumber", 0);

            // update UI accordingly
            binding.goalName.setText(goalName);
            binding.date.setText(date);
            binding.goalImage.setImageResource(categoryImages[goalImage]);

            ProgressBar progressBar = binding.progressBar;
            int CurrentProgress = (int) ((float) goalCurrent / goalNumber * 100);
            progressBar.setProgress(CurrentProgress);

            if (CurrentProgress >= 100) {
                binding.reachGoal.setVisibility(View.VISIBLE);
            } else {
                binding.reachGoal.setVisibility(View.GONE);
            }

            binding.goalCurrent.setText(String.format("%,d", goalCurrent));
            binding.goalNumber.setText(String.format("%,d", goalNumber));
            binding.balance.setText("10,000,000 đ");
        }

        binding.closeButton.setOnClickListener(view -> onBackPressed());

        binding.deleteButton.setOnClickListener(view -> showDeleteConfirmationDialog());

        binding.editButton.setOnClickListener(view -> {
            Intent editIntent = new Intent(GoalFund.this, GoalEdit.class);
            editIntent.putExtra("goalID", getIntent().getStringExtra("goalID"));
            GoalFund.this.startActivity(editIntent);
        });

        binding.saveButton.setOnClickListener(view -> {
            String goalCurrentText = binding.inputGoalCurrent.getText().toString();
            String balanceText = binding.balance.getText().toString();

            Long goalCurrent = formatStringToNumber(goalCurrentText);
            Long balance = formatStringToNumber(balanceText);

            if (binding.inputGoalCurrent.getText().toString().isEmpty()) {
                Toast.makeText(GoalFund.this, "Vui lòng nhập số tiền!", Toast.LENGTH_SHORT).show();
            } else if (goalCurrent > balance) {
                Toast.makeText(GoalFund.this, "Không đủ số dư!", Toast.LENGTH_SHORT).show();
            } else {
                // Mô phỏng cập nhật thành công
                Toast.makeText(GoalFund.this, "Cập nhật quỹ mục tiêu thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void updateUIWithMockData() {
        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        // Dữ liệu mẫu được cập nhật
        binding.goalName.setText("Mua xe");
        binding.date.setText("15-12-2023");
        binding.goalImage.setImageResource(categoryImages[2]);

        Long goalCurrent = 70000000L;
        Long goalNumber = 150000000L;

        ProgressBar progressBar = binding.progressBar;
        int CurrentProgress = (int) ((float) goalCurrent / goalNumber * 100);
        progressBar.setProgress(CurrentProgress);

        binding.goalCurrent.setText(String.format("%,d", goalCurrent));
        binding.goalNumber.setText(String.format("%,d", goalNumber));
    }

    private void deleteGoal() {
        // Mô phỏng xóa mục tiêu
        Toast.makeText(GoalFund.this, "Xóa mục tiêu thành công", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showDeleteConfirmationDialog() {
        // Tạo AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.ui_delete_goal, null);
        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        Button deleteButton = dialogView.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(view -> {
            deleteGoal();
            dialog.dismiss();
        });

        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();
    }

    private Long formatStringToNumber(String formattedAmount) {
        String amountString = formattedAmount.replaceAll("[,.\\sđ]", "");
        try {
            return Long.parseLong(amountString);
        } catch (NumberFormatException e) {
            return 0L;
        }
    }
}