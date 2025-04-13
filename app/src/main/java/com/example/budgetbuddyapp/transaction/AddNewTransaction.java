package com.example.budgetbuddyapp.transaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.categories.Category;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewTransaction extends AppCompatActivity {
    private static final String TAG = "AddNewTransaction";

    ImageView categoryIcon, closeButton, addNewTransaction;
    EditText transactionAmount, note;
    TextView date, time;
    Calendar calendar;
    Category selectedCategory;
    int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
            R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
            R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2,
            R.drawable.c_basketball, R.drawable.c_gardening};

    private void getSelectedCategoryFromLocal() {
        // Mẫu dữ liệu danh mục cho frontend
        selectedCategory = new Category("5", "user1", "Ăn uống", "Chi tiêu", 0, true);
        categoryIcon.setImageResource(categoryImages[selectedCategory.getCategoryImage()]);
    }

    private Long formatStringToNumber(String formatedAmount) {
        String amountString = formatedAmount.replaceAll("[,.]", "");
        try {
            return Long.parseLong(amountString);
        } catch (NumberFormatException e) {
            Log.e(TAG, "Error parsing amount: " + e.getMessage());
            return 0L;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_transaction);

        closeButton = findViewById(R.id.closeButton);
        categoryIcon = findViewById(R.id.categoryIcon);
        transactionAmount = findViewById(R.id.transactionAmount);
        note = findViewById(R.id.note);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        addNewTransaction = findViewById(R.id.addNewTransaction);

        calendar = Calendar.getInstance();
        updateDateInView();
        updateTimeInView();

        // Lấy danh mục mặc định
        getSelectedCategoryFromLocal();

        // Định dạng số tiền
        transactionAmount.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        transactionAmount.addTextChangedListener(new TextWatcher() {
            private DecimalFormat decimalFormat = new DecimalFormat("#,##0");
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện gì
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Không cần thực hiện gì
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    transactionAmount.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[,.]", "");
                    try {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = decimalFormat.format(parsed);

                        current = formatted;
                        transactionAmount.setText(formatted);
                        transactionAmount.setSelection(formatted.length());
                    } catch (NumberFormatException e) {
                        // Xử lý nếu không thể parse sang số double
                    }

                    transactionAmount.addTextChangedListener(this);
                }
            }
        });

        time.setOnClickListener(view -> showTimePickerDialog());

        date.setOnClickListener(view -> showDatePickerDialog());

        closeButton.setOnClickListener(view -> finish());

        categoryIcon.setOnClickListener(view -> {
            // Hiển thị bottom sheet để chọn danh mục
            ChooseCategoryBottomSheet bottomSheet = new ChooseCategoryBottomSheet(selectedCategory);
            bottomSheet.show(getSupportFragmentManager(), "ChooseCategoryBottomSheet");
        });

        addNewTransaction.setOnClickListener(view -> {
            if (transactionAmount.getText().toString().isEmpty()) {
                Toast.makeText(AddNewTransaction.this, "Vui lòng nhập số tiền giao dịch!", Toast.LENGTH_SHORT).show();
                return;
            }

            Long amount = formatStringToNumber(transactionAmount.getText().toString());
            String noteText = note.getText().toString();
            String dateText = date.getText().toString();
            String timeText = time.getText().toString();

            // Mô phỏng thêm giao dịch vào local và cập nhật số dư người dùng
            updateUserBalance(amount, selectedCategory.getCategoryType());

            Toast.makeText(AddNewTransaction.this, "Thêm giao dịch thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void updateUserBalance(long amount, String categoryType) {
        // Giả lập cập nhật số dư người dùng
        if (categoryType.equals("Thu nhập")) {
            // Tăng số dư nếu là thu nhập
            Log.d(TAG, "Số dư tăng: +" + amount);
        } else {
            // Giảm số dư nếu là chi tiêu
            Log.d(TAG, "Số dư giảm: -" + amount);
        }
    }

    public void showTimePickerDialog() {
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this,
                (view, selectedHour, selectedMinute) -> {
                    calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
                    calendar.set(Calendar.MINUTE, selectedMinute);
                    updateTimeInView();
                },
                hour,
                minute,
                true
        );

        timePickerDialog.show();
    }

    private void updateTimeInView() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        time.setText(sdf.format(calendar.getTime()));
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
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        date.setText(sdf.format(calendar.getTime()));
    }

    public void updateCategory(Category category) {
        this.selectedCategory = category;
        categoryIcon.setImageResource(categoryImages[category.getCategoryImage()]);
    }
}