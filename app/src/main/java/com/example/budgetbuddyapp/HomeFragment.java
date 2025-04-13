package com.example.budgetbuddyapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.budgetbuddyapp.categories.CategoryHome;
import com.example.budgetbuddyapp.transaction.HomeTransactionAdapter;
import com.example.budgetbuddyapp.transaction.RecentTransaction;
import com.example.budgetbuddyapp.transaction.Transaction;
import com.google.android.material.tabs.TabLayout;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "HomeFragment";

    private String mParam1;
    private String mParam2;

    TextView fullName, balance, categoryViewAll, transactionViewAll;
    ImageView hideBalance;
    String userID = "user1";
    ArrayList<Transaction> transactionList;
    HomeTransactionAdapter adapter;
    ListView recentTrasactions;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    ViewPagerAdapter CategoryApdater;

    public static final String SHARED_PREFS = "sharePrefs";

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.home_screen, container, false);

        fullName = view.findViewById(R.id.txtViewUserName);
        balance = view.findViewById(R.id.balance);
        hideBalance = view.findViewById(R.id.hideBalance);
        categoryViewAll = view.findViewById(R.id.categoryViewAll);
        transactionViewAll = view.findViewById(R.id.transactionViewAll);
        recentTrasactions = view.findViewById(R.id.recentTrasactions);
        transactionList = new ArrayList<>();
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager = view.findViewById(R.id.viewPager);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        CategoryApdater = new ViewPagerAdapter(fragmentManager, getLifecycle());
        TextView noItem = view.findViewById(R.id.noItem);
        viewPager.setAdapter(CategoryApdater);
        final boolean[] isPasswordVisible = {false};

        // Thiết lập dữ liệu mẫu
        fullName.setText("Nguyễn Văn A");
        balance.setText("5,000,000 đ");

        tabLayout.addTab(tabLayout.newTab().setText("CHI TIÊU"));
        tabLayout.addTab(tabLayout.newTab().setText("THU NHẬP"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        hideBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPasswordVisible[0]) {
                    balance.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isPasswordVisible[0] = false;
                } else {
                    balance.setTransformationMethod(null);
                    isPasswordVisible[0] = true;
                }
            }
        });

        categoryViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CategoryHome.class));
            }
        });

        transactionViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), RecentTransaction.class));
            }
        });

        // Tạo dữ liệu giao dịch mẫu
        loadMockTransactions();

        if (transactionList.isEmpty()) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }

        adapter = new HomeTransactionAdapter(this, R.layout.transaction_item, transactionList);
        recentTrasactions.setAdapter(adapter);

        return view;
    }

    private void loadMockTransactions() {
        // Tạo dữ liệu mẫu cho các giao dịch gần đây
        transactionList.add(new Transaction("1", userID, "cat1", "Ăn trưa", "17-07-2024", "12:30", 150000L));
        transactionList.add(new Transaction("2", userID, "cat2", "Mua sắm", "16-07-2024", "15:45", 500000L));
        transactionList.add(new Transaction("3", userID, "cat3", "Tiền lương", "15-07-2024", "08:00", 10000000L));
        transactionList.add(new Transaction("4", userID, "cat4", "Tiền điện", "14-07-2024", "18:20", 300000L));
    }
}