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

public class TransactionOutcomeFragment extends Fragment {
    private ListView listView;
    private ArrayList<Category> categoryList;
    private TransactionCategoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_outcome_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        categoryList = new ArrayList<>();
        listView = (ListView) view.findViewById(R.id.listview);
        TextView noItem = (TextView) view.findViewById(R.id.noItem);

        // Thêm các danh mục chi tiêu mẫu
        categoryList.add(new Category("5", "user1", "Ăn uống", "Chi tiêu", 0, false));
        categoryList.add(new Category("6", "user1", "Điện nước", "Chi tiêu", 1, false));
        categoryList.add(new Category("7", "user1", "Xăng dầu", "Chi tiêu", 2, false));
        categoryList.add(new Category("8", "user1", "Quần áo", "Chi tiêu", 3, false));
        categoryList.add(new Category("9", "user1", "Mua sắm", "Chi tiêu", 5, false));

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