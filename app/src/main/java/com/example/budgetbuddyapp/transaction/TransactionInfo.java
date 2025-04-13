package com.example.budgetbuddyapp.transaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import com.example.budgetbuddyapp.R;

public class TransactionInfo extends AppCompatActivity {

    ImageView categoryIcon, editTransaction, deleteTransaction, closeButton;
    TextView categoryName, amount, date, time, note;

    int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
            R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
            R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2,
            R.drawable.c_basketball, R.drawable.c_gardening};

    String categoryType;
    String categoryID;
    String transactionID;
    String noteI;
    long amountI;
    String dateI;
    String timeI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_info);

        closeButton = findViewById(R.id.closeButton);
        editTransaction = findViewById(R.id.editTransaction);
        deleteTransaction = findViewById(R.id.deleteTransaction);
        categoryIcon = findViewById(R.id.categoryIcon);
        categoryName = findViewById(R.id.categoryName);
        amount = findViewById(R.id.amount);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);

        closeButton.setOnClickListener(view -> onBackPressed());

        Intent intent = getIntent();
        if (intent != null) {
            transactionID = intent.getStringExtra("TransactionID");
            categoryID = intent.getStringExtra("CategoryID");

            // Load dữ liệu mẫu cho frontend
            loadMockData();

            deleteTransaction.setOnClickListener(view ->
                    showDeleteConfirmationDialog(transactionID, categoryID, amountI));

            editTransaction.setOnClickListener(view -> {
                Intent editIntent = new Intent(TransactionInfo.this, EditTransaction.class);

                editIntent.putExtra("TransactionID", transactionID);
                editIntent.putExtra("CategoryID", categoryID);
                editIntent.putExtra("Note", noteI);
                editIntent.putExtra("Amount", amountI);
                editIntent.putExtra("Date", dateI);
                editIntent.putExtra("Time", timeI);

                startActivity(editIntent);
            });
        }
    }

    private void loadMockData() {
        // Dữ liệu giao dịch mẫu
        dateI = "20-05-2023";
        timeI = "14:30";
        noteI = "Mua sắm tại siêu thị";
        amountI = 250000;

        date.setText(dateI);
        time.setText(timeI);
        if (noteI.isEmpty()) {
            note.setText("Không có");
        } else {
            note.setText(noteI);
        }

        // Dữ liệu danh mục mẫu
        int categoryImage = 0; // Food
        String categoryNameText = "Ăn uống";
        categoryType = "Chi tiêu";

        categoryName.setText(categoryNameText);
        categoryIcon.setImageResource(categoryImages[categoryImage]);

        if (categoryType.equals("Thu nhập")) {
            int color = ContextCompat.getColor(TransactionInfo.this, R.color.earn);
            amount.setTextColor(color);
            amount.setText("+" + String.format("%,d", amountI));
        } else {
            int color = ContextCompat.getColor(TransactionInfo.this, R.color.spend);
            amount.setTextColor(color);
            amount.setText("-" + String.format("%,d", amountI));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload dữ liệu khi quay lại màn hình
        loadMockData();
    }

    private void showDeleteConfirmationDialog(String transactionId, String categoryId, Long amount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TransactionInfo.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn có chắc chắn muốn xóa giao dịch này không?");

        builder.setPositiveButton("Xác nhận", (dialog, which) -> {
            // Mô phỏng xóa giao dịch thành công
            Toast.makeText(getApplicationContext(), "Xóa giao dịch thành công!", Toast.LENGTH_SHORT).show();
            finish();
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}