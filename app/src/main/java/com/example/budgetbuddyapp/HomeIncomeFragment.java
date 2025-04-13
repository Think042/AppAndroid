package com.example.budgetbuddyapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.budgetbuddyapp.categories.Category;
import java.util.ArrayList;

public class HomeIncomeFragment extends Fragment {
    String userID = "user1";
    ListView listView;
    ArrayList<Category> categoryList;
    HomeCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_income, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);
        TextView noItem = view.findViewById(R.id.noItem);

        // Tạo dữ liệu mẫu danh mục thu nhập
        categoryList.add(new Category("cat1", userID, "Lương", "Thu nhập", 7));
        categoryList.add(new Category("cat2", userID, "Thưởng", "Thu nhập", 4));
        categoryList.add(new Category("cat3", userID, "Đầu tư", "Thu nhập", 8));
        categoryList.add(new Category("cat4", userID, "Tiết kiệm", "Thu nhập", 5));

        if (categoryList.isEmpty()) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }

        adapter = new HomeCategoryAdapter(getActivity(), R.layout.home_category_item, categoryList, getContext());
        listView.setAdapter(adapter);
    }
}