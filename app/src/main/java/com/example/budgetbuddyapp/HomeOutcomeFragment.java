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

public class HomeOutcomeFragment extends Fragment {
    String userID = "user1";
    ListView listView;
    ArrayList<Category> categoryList;
    HomeCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_outcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);
        TextView noItem = view.findViewById(R.id.noItem);

        // Tạo dữ liệu mẫu danh mục chi tiêu
        categoryList.add(new Category("cat5", userID, "Ăn uống", "Chi tiêu", 0));
        categoryList.add(new Category("cat6", userID, "Tiện ích", "Chi tiêu", 1));
        categoryList.add(new Category("cat7", userID, "Mua sắm", "Chi tiêu", 5));
        categoryList.add(new Category("cat8", userID, "Giải trí", "Chi tiêu", 10));
        categoryList.add(new Category("cat9", userID, "Di chuyển", "Chi tiêu", 2));

        if (categoryList.isEmpty()) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }

        adapter = new HomeCategoryAdapter(getActivity(), R.layout.home_category_item, categoryList, getContext());
        listView.setAdapter(adapter);
    }
}