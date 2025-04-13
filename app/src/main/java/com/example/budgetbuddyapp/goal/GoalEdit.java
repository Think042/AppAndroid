package com.example.budgetbuddyapp.goal;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.databinding.UiEditGoalBinding;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class GoalEdit extends AppCompatActivity {

    UiEditGoalBinding binding;
    TextView date;
    Calendar calendar;
    int[] iconURL = {0};
    int[] goalImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
            R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
            R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UiEditGoalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        date = findViewById(R.id.date);
        calendar = Calendar.getInstance();

        Intent intent = getIntent();
        if (intent != null) {
            String goalID = intent.getStringExtra("goalID");
            fetchGoalData();
        }

        // Hiển thị các biểu tượng của người dùng
        GoalGridViewAdapter gridAdapter = new GoalGridViewAdapter(GoalEdit.this, goalImages);
        binding.gridview.setAdapter(gridAdapter);

        binding.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                binding.goalImage.setImageResource(goalImages[position]);
                iconURL[0] = position;
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.inputGoalName.getText().toString().equals("")) {
                    Toast.makeText(GoalEdit.this, "Vui lòng nhập tên mục tiêu!", Toast.LENGTH_SHORT).show();
                } else if (binding.inputGoalNumber.getText().toString().equals("")) {
                    Toast.makeText(GoalEdit.this, "Vui lòng nhập số tiền mục tiêu!", Toast.LENGTH_SHORT).show();
                } else {
                    // Mô phỏng cập nhật thành công
                    Toast.makeText(GoalEdit.this, "Cập nhật mục tiêu thành công!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }

    private Long formatStringToNumber(String formatedAmount) {
        String amountString = formatedAmount.replaceAll("[,.]", "");
        try {
            Long amount = Long.parseLong(amountString);
            return amount;
        } catch (NumberFormatException e) {
            return 0L;
        }
    }

    public void showDatePickerDialog() {
        new DatePickerDialog(this, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDateInView();
        }
    };

    private void updateDateInView() {
        SimpleDateFormat sdf = new SimpleDateFormat("d-M-yyyy", Locale.getDefault());
        date.setText(sdf.format(calendar.getTime()));
    }

    private void fetchGoalData() {
        // Dữ liệu mẫu cho màn hình chỉnh sửa
        binding.inputGoalName.setText("Mua xe");
        binding.date.setText("15-12-2023");
        binding.goalImage.setImageResource(goalImages[2]);
        iconURL[0] = 2;
        binding.inputGoalNumber.setText("150000000");
    }
}