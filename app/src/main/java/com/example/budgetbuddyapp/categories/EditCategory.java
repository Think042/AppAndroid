package com.example.budgetbuddyapp.categories;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import com.example.budgetbuddyapp.R;
import com.example.budgetbuddyapp.databinding.ActivityEditCategoryBinding;

public class EditCategory extends AppCompatActivity {
    private Category selectedCategory;
    ActivityEditCategoryBinding binding;
    int iconURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iconURL = 0;

        // Lấy dữ liệu Category từ Intent
        Intent intent = getIntent();

        int[] categoryImages = {R.drawable.food, R.drawable.c_electricitybill, R.drawable.c_fuel, R.drawable.c_clothes,
                R.drawable.c_bonus, R.drawable.c_shopping, R.drawable.c_book, R.drawable.c_salary, R.drawable.c_wallet,
                R.drawable.c_phone, R.drawable.c_celebration, R.drawable.c_makeup, R.drawable.c_celebration2, R.drawable.c_basketball, R.drawable.c_gardening};

        if (intent != null && intent.hasExtra("category")) {
            selectedCategory = (Category) intent.getSerializableExtra("category");
            if (selectedCategory != null) {
                binding.inputCategoryName.setText(selectedCategory.getCategoryName());
                iconURL = selectedCategory.getCategoryImage();
                binding.categoryIcon.setImageResource(categoryImages[iconURL]);

                binding.save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        updateCategory(selectedCategory);
                    }
                });
            }
        }

        CategoryGridViewAdapter gridAdapter = new CategoryGridViewAdapter(EditCategory.this, categoryImages);
        binding.gridview.setAdapter(gridAdapter);

        binding.gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                binding.categoryIcon.setImageResource(categoryImages[position]);
                iconURL = position;
                selectedCategory.setCategoryImage(position);
            }
        });

        binding.closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void updateCategory(Category category) {
        // Cập nhật thuộc tính cho category
        category.setCategoryName(binding.inputCategoryName.getText().toString());
        category.setCategoryImage(iconURL);

        // Hiển thị thông báo thành công
        Toast.makeText(getApplicationContext(), "Cập nhật loại chi tiêu thành công!", Toast.LENGTH_SHORT).show();
        finish();
    }
}