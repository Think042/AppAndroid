package com.example.budgetbuddyapp.transaction;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.categories.Category;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionAdapter extends ArrayAdapter<Transaction> {
    private Activity activity;
    private ArrayList<Transaction> transactionList;
    private Context context;

    // Map để lưu trữ thông tin danh mục
    private static final Map<String, Category> categoryMap = new HashMap<>();

    static {
        // Các danh mục mẫu
        categoryMap.put("1", new Category("1", "user1", "Lương", "Thu nhập", 7, false));
        categoryMap.put("2", new Category("2", "user1", "Thưởng", "Thu nhập", 4, false));
        categoryMap.put("3", new Category("3", "user1", "Đầu tư", "Thu nhập", 8, false));
        categoryMap.put("4", new Category("4", "user1", "Quà tặng", "Thu nhập", 10, false));
        categoryMap.put("5", new Category("5", "user1", "Ăn uống", "Chi tiêu", 0, false));
        categoryMap.put("6", new Category("6", "user1", "Điện nước", "Chi tiêu", 1, false));
        categoryMap.put("7", new Category("7", "user1", "Xăng dầu", "Chi tiêu", 2, false));
        categoryMap.put("8", new Category("8", "user1", "Quần áo", "Chi tiêu", 3, false));
        categoryMap.put("9", new Category("9", "user1", "Mua sắm", "Chi tiêu", 5, false));
    }

    int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
            R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
            R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2,
            R.drawable.c_basketball, R.drawable.c_gardening};

    public TransactionAdapter(Activity activity, int layoutID, ArrayList<Transaction> transactionList, Context context) {
        super(activity, layoutID, transactionList);
        this.activity = activity;
        this.transactionList = transactionList;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(activity).inflate(R.layout.transaction_item, null, false);
        }

        TextView categoryName = (TextView) view.findViewById(R.id.categoryName);
        ImageView categoryIcon = (ImageView) view.findViewById(R.id.categoryIcon);
        TextView transactionDate = (TextView) view.findViewById(R.id.transactionDate);
        TextView transactionNote = (TextView) view.findViewById(R.id.transactionNote);
        TextView transactionAmount = (TextView) view.findViewById(R.id.transactionAmount);

        Transaction transaction = getItem(position);
        String categoryId = transaction.getCategoryId();

        if (categoryId != null && categoryMap.containsKey(categoryId)) {
            Category category = categoryMap.get(categoryId);

            categoryName.setText(category.getCategoryName());
            categoryIcon.setImageResource(categoryImages[category.getCategoryImage()]);

            if (category.getCategoryType().equals("Thu nhập")) {
                int color = ContextCompat.getColor(getContext(), R.color.earn);
                transactionAmount.setTextColor(color);
                String amount = "+" + String.format("%,d", transaction.getAmount());
                transactionAmount.setText(amount);
            } else {
                int color = ContextCompat.getColor(getContext(), R.color.spend);
                transactionAmount.setTextColor(color);
                String amount = "-" + String.format("%,d", transaction.getAmount());
                transactionAmount.setText(amount);
            }
        }

        transactionDate.setText(transaction.getDate());
        transactionNote.setText(transaction.getNote());
        return view;
    }
}