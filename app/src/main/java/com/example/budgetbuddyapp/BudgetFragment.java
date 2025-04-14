package com.example.budgetbuddyapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.budgetbuddyapp.expense.Expense;
import com.example.budgetbuddyapp.expense.ExpenseAdapter;
import com.example.budgetbuddyapp.expense.ExpenseProgress;
import com.example.budgetbuddyapp.goal.AddNewGoal;
import com.example.budgetbuddyapp.goal.GoalAdapter;
import com.example.budgetbuddyapp.goal.Goal;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class BudgetFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final String TAG = "BudgetFragment";

    String userID = "user1";
    ListView expenseListView, goalListView;
    ArrayList<Expense> expenseList;
    ArrayList<Goal> goalList;
    TextView balance, noExpense, noGoal;
    Button addNewGoal;
    View view;
    com.example.budgetbuddyapp.expense.ExpenseAdapter ExpenseAdapter;
    com.example.budgetbuddyapp.goal.GoalAdapter GoalAdapter;
    private static final int REQUEST_CODE = 1;

    public BudgetFragment() {
        // Required empty public constructor
    }

    //Khởi tạo Fragment
    public static BudgetFragment newInstance(String param1, String param2) {
        BudgetFragment fragment = new BudgetFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ui_budget, container, false);

        expenseListView = view.findViewById(R.id.expenseListView);
        noExpense = view.findViewById(R.id.noExpense);
        expenseList = new ArrayList<Expense>();

        goalListView = view.findViewById(R.id.goalListView);
        noGoal = view.findViewById(R.id.noGoal);
        goalList = new ArrayList<Goal>();

        balance = view.findViewById(R.id.balance);
        // Hiển thị dữ liệu mẫu cho số dư
        balance.setText("5,000,000 đ");

        addNewGoal = view.findViewById(R.id.addNewGoal);
        addNewGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), AddNewGoal.class);
                startActivity(intent);
            }
        });

        loadGoal();
        loadExpense();

        return view;
    }

    private void loadExpense() {
        // Xóa dữ liệu cũ
        expenseList.clear();

        // Tạo dữ liệu mẫu cho chi tiêu
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM - yyyy", Locale.getDefault());
        String currentMonthYear = dateFormat.format(calendar.getTime());

        // Thêm các mục chi tiêu mẫu
        expenseList.add(new Expense("1", userID, "Ăn uống", 0, "cat1"));
        expenseList.add(new ExpenseProgress("2", userID, "Mua sắm", 5, "cat2", currentMonthYear, 1000000, 450000));
        expenseList.add(new Expense("3", userID, "Tiện ích", 1, "cat3"));
        expenseList.add(new ExpenseProgress("4", userID, "Giải trí", 10, "cat4", currentMonthYear, 500000, 200000));

        // Hiển thị thông báo nếu không có dữ liệu
        if (expenseList.isEmpty()) {
            noExpense.setVisibility(View.VISIBLE);
        } else {
            noExpense.setVisibility(View.GONE);
        }

        // Tạo adapter và gán vào ListView
        ExpenseAdapter = new ExpenseAdapter(view.getContext(), R.layout.item_expense, expenseList);
        expenseListView.setAdapter(ExpenseAdapter);
    }

    private void loadGoal() {
        // Xóa dữ liệu cũ
        goalList.clear();

        // Tạo dữ liệu mẫu cho mục tiêu
        goalList.add(new Goal("1", userID, "Mua laptop", 5000000L, 15000000L, 6, "31-12-2024"));
        goalList.add(new Goal("2", userID, "Du lịch", 2000000L, 10000000L, 12, "30-06-2025"));
        goalList.add(new Goal("3", userID, "Đầu tư", 15000000L, 50000000L, 8, "01-01-2025"));

        // Hiển thị thông báo nếu không có dữ liệu
        if (goalList.isEmpty()) {
            noGoal.setVisibility(View.VISIBLE);
        } else {
            noGoal.setVisibility(View.GONE);
        }

        // Tạo adapter và gán vào ListView
        GoalAdapter = new GoalAdapter(this, R.layout.item_goal, goalList);
        goalListView.setAdapter(GoalAdapter);
    }
}