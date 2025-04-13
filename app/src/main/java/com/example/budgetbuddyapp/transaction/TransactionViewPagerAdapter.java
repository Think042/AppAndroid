package com.example.budgetbuddyapp.transaction;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TransactionViewPagerAdapter extends FragmentStateAdapter {
    public TransactionViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new TransactionOutcomeFragment();
        } else {
            return new TransactionIncomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2; // Có 2 tab: Chi tiêu và Thu nhập
    }
}