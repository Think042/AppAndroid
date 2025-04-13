package com.example.budgetbuddyapp.categories;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.example.budgetbuddyapp.R;
import java.util.ArrayList;

public class OutcomeFragment extends Fragment {
    ListView listView;
    ArrayList<Category> categoryList;
    CategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_outcome, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryList = new ArrayList<>();
        listView = view.findViewById(R.id.listview);
        TextView noItem = view.findViewById(R.id.noItem);

        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        // Thêm dữ liệu mẫu tĩnh
        categoryList.add(new Category("1", "user1", "Thực phẩm", "Chi tiêu", 0));
        categoryList.add(new Category("2", "user1", "Điện nước", "Chi tiêu", 1));
        categoryList.add(new Category("3", "user1", "Xăng dầu", "Chi tiêu", 2));
        categoryList.add(new Category("4", "user1", "Quần áo", "Chi tiêu", 3));
        categoryList.add(new Category("5", "user1", "Mua sắm", "Chi tiêu", 5));

        if (categoryList.isEmpty()) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }

        adapter = new CategoryAdapter(getActivity(), R.layout.category_item, categoryList, getContext());
        listView.setAdapter(adapter);
    }
}