package com.example.budgetbuddyapp.transaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.budgetbuddyapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class RecentTransaction extends AppCompatActivity {
    private static final String PREFS_NAME = "TransactionPrefs";
    private ListView transactionListView;
    private ArrayList<Transaction> transactionList;
    private TransactionAdapter adapter;
    private ImageView backButton;
    private FloatingActionButton addNewTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recent_transaction);

        backButton = findViewById(R.id.backButton);
        addNewTransaction = findViewById(R.id.addNewTransaction);
        transactionListView = findViewById(R.id.transactionListView);

        transactionList = new ArrayList<>();

        // Thêm dữ liệu giao dịch mẫu
        loadSampleTransactions();

        adapter = new TransactionAdapter(this, R.layout.transaction_item, transactionList, getApplicationContext());
        transactionListView.setAdapter(adapter);

        addNewTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RecentTransaction.this, AddNewTransaction.class));
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        transactionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Transaction selectedTransaction = transactionList.get(position);

                Intent intent = new Intent(RecentTransaction.this, TransactionInfo.class);

                intent.putExtra("TransactionID", selectedTransaction.getTransactionId());
                intent.putExtra("CategoryID", selectedTransaction.getCategoryId());
                intent.putExtra("Note", selectedTransaction.getNote());
                intent.putExtra("Amount", selectedTransaction.getAmount());
                intent.putExtra("Date", selectedTransaction.getDate());
                intent.putExtra("Time", selectedTransaction.getTime());

                startActivity(intent);
            }
        });
    }

    private void loadSampleTransactions() {
        // Thêm dữ liệu giao dịch mẫu
        transactionList.add(new Transaction("1", "user1", "5", "Ăn trưa", "25-06-2023", "12:30", 150000L));
        transactionList.add(new Transaction("2", "user1", "1", "Lương tháng 6", "01-06-2023", "09:00", 10000000L));
        transactionList.add(new Transaction("3", "user1", "7", "Đổ xăng", "20-06-2023", "18:15", 200000L));
        transactionList.add(new Transaction("4", "user1", "6", "Tiền điện", "15-06-2023", "20:30", 500000L));
        transactionList.add(new Transaction("5", "user1", "8", "Quần áo mới", "10-06-2023", "15:45", 800000L));
        transactionList.add(new Transaction("6", "user1", "2", "Thưởng dự án", "05-06-2023", "17:00", 2000000L));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Nếu cần, có thể cập nhật lại danh sách ở đây
    }
}