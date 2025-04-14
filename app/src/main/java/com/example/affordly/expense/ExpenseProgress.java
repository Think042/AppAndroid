package com.example.affordly.expense;

public class ExpenseProgress extends Expense {

    public ExpenseProgress(String expenseID, String userID, String expenseName, int expenseImage, String categoryID, String expenseTime, int expenseLimit, int expenseCurrent) {
        super(expenseID, userID, expenseName, expenseImage, categoryID);
        this.expenseTime = expenseTime;
        this.expenseLimit = expenseLimit;
        this.expenseCurrent = expenseCurrent;
    }
}
