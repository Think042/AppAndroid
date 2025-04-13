package com.example.budgetbuddyapp.transaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.categories.Category;
import java.util.ArrayList;

public class TransactionIncomeFragment extends Fragment {
    private ListView listView;
    private ArrayList<Category> categoryList;
    private TransactionCategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_income_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryList = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.listview);
        TextView noItem = (TextView) view.findViewById(R.id.noItem);

        // Thêm các danh mục thu nhập mẫu
        categoryList.add(new Category("1", "user1", "Lương", "Thu nhập", 7, false));
        categoryList.add(new Category("2", "user1", "Thưởng", "Thu nhập", 4, false));
        categoryList.add(new Category("3", "user1", "Đầu tư", "Thu nhập", 8, false));
        categoryList.add(new Category("4", "user1", "Quà tặng", "Thu nhập", 10, false));

        if (categoryList.isEmpty()) {
            noItem.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.GONE);
        }

        adapter = new TransactionCategoryAdapter(getActivity(), R.layout.transaction_category_item, categoryList, getContext());
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Category selectedCategory = categoryList.get(position);

            // Đặt tất cả các danh mục khác thành không được chọn
            for (Category category : categoryList) {
                category.setSelected(false);
            }

            // Đặt danh mục được chọn thành true
            selectedCategory.setSelected(true);

            // Cập nhật Adapter để áp dụng thay đổi lên giao diện người dùng
            adapter.notifyDataSetChanged();

            // Gửi dữ liệu biểu tượng của danh mục đã chọn về cho ChooseCategoryBottomSheet
            ((ChooseCategoryBottomSheet) getParentFragment()).updateCategory(selectedCategory);
        });
    }
}