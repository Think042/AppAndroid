package com.example.budgetbuddyapp;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.budgetbuddyapp.transaction.Transaction;
import com.example.budgetbuddyapp.transaction.TransactionAdapter;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ReportFragment extends Fragment {
    private static final String TAG = "ReportFragment";
    private String userID = "user1";

    private LineChart lineChart;
    private List<String> xValues;
    private List<Float> outcome7days = new ArrayList<>();
    private List<Float> income7days = new ArrayList<>();
    float maxHeight = 10000000f; // Giá trị mẫu
    List<String> sevenDays;
    PieChart pieChartExpense, pieChartRevenue;
    TextView tv_expense_number, tv_revenue_number, balance;
    ListView revenueListView, expenseListView;

    public ReportFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_report, container, false);

        pieChartExpense = view.findViewById(R.id.pieChartExpense);
        pieChartRevenue = view.findViewById(R.id.pieChartRevenue);

        tv_expense_number = view.findViewById(R.id.tv_expense_number);
        tv_revenue_number = view.findViewById(R.id.tv_revenue_number);

        revenueListView = view.findViewById(R.id.recentRevenue);
        expenseListView = view.findViewById(R.id.recentExpense);

        lineChart = view.findViewById(R.id.linechart);
        balance = view.findViewById(R.id.balance);

        // Cài đặt số dư mẫu
        balance.setText("5,000,000 đ");

        // Khởi tạo và tải dữ liệu mẫu cho các biểu đồ
        initLinechart();
        loadMockDataForCharts();

        return view;
    }

    private void initLinechart() {
        // Danh sách ngày trong 7 ngày gần đây
        sevenDays = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_YEAR, -i);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
            final String dateString = dateFormat.format(calendar.getTime());

            sevenDays.add(dateString);

            // Dữ liệu mẫu cho thu nhập và chi tiêu
            income7days.add((float)(Math.random() * 1000000));
            outcome7days.add((float)(Math.random() * 800000));
        }

        Description description = new Description();
        description.setText("");
        description.setPosition(150f, 15f);
        lineChart.setDescription(description);
        lineChart.getAxisRight().setDrawLabels(false);

        xValues = Arrays.asList(
                sevenDays.get(6).substring(0,5),
                sevenDays.get(5).substring(0,5),
                sevenDays.get(4).substring(0,5),
                sevenDays.get(3).substring(0,5),
                sevenDays.get(2).substring(0,5),
                sevenDays.get(1).substring(0,5),
                sevenDays.get(0).substring(0,5)
        );

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xValues));
        xAxis.setLabelCount(7);
        xAxis.setGranularity(1f);

        updateLineChart();
    }

    private void updateLineChart() {
        YAxis yAxis = lineChart.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(maxHeight);
        yAxis.setAxisLineWidth(2f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        List<Entry> incomeEntry = new ArrayList<>();
        List<Entry> outcomeEntry = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            incomeEntry.add(new Entry(i, income7days.get(6-i)));
            outcomeEntry.add(new Entry(i, outcome7days.get(6-i)));
        }

        LineDataSet dataSet = new LineDataSet(incomeEntry, "Thu nhập");
        dataSet.setColors(Color.parseColor("#FF00BD40"));

        LineDataSet dataSet1 = new LineDataSet(outcomeEntry, "Chi tiêu");
        dataSet1.setColors(Color.parseColor("#FFFF1D1D"));

        LineData lineData = new LineData(dataSet, dataSet1);
        lineChart.setData(lineData);
        lineChart.invalidate();
    }

    private void loadMockDataForCharts() {
        // Dữ liệu mẫu cho biểu đồ tròn thu nhập
        List<PieEntry> incomeEntries = new ArrayList<>();
        incomeEntries.add(new PieEntry(4000000f, "Lương"));
        incomeEntries.add(new PieEntry(1000000f, "Thưởng"));
        incomeEntries.add(new PieEntry(500000f, "Đầu tư"));
        updatePieChart(pieChartRevenue, incomeEntries, "Thu nhập");
        tv_revenue_number.setText("5,500,000");
        tv_revenue_number.setTextColor(Color.parseColor("#FF00BD40"));

        // Dữ liệu mẫu cho biểu đồ tròn chi tiêu
        List<PieEntry> expenseEntries = new ArrayList<>();
        expenseEntries.add(new PieEntry(1500000f, "Ăn uống"));
        expenseEntries.add(new PieEntry(1000000f, "Tiện ích"));
        expenseEntries.add(new PieEntry(800000f, "Mua sắm"));
        expenseEntries.add(new PieEntry(500000f, "Giải trí"));
        updatePieChart(pieChartExpense, expenseEntries, "Chi tiêu");
        tv_expense_number.setText("3,800,000");
        tv_expense_number.setTextColor(Color.parseColor("#FFFF1D1D"));

        // Dữ liệu mẫu cho danh sách giao dịch thu nhập
        ArrayList<Transaction> incomeTrans = new ArrayList<>();
        incomeTrans.add(new Transaction("1", userID, "cat1", "Lương tháng 7", "15-07-2024", "08:00", 4000000L));
        incomeTrans.add(new Transaction("2", userID, "cat2", "Thưởng dự án", "12-07-2024", "17:30", 1000000L));
        incomeTrans.add(new Transaction("3", userID, "cat3", "Cổ tức", "10-07-2024", "10:15", 500000L));

        TransactionAdapter incomeAdapter = new TransactionAdapter(getActivity(), R.layout.transaction_item, incomeTrans, getContext());
        revenueListView.setAdapter(incomeAdapter);

        // Dữ liệu mẫu cho danh sách giao dịch chi tiêu
        ArrayList<Transaction> expenseTrans = new ArrayList<>();
        expenseTrans.add(new Transaction("4", userID, "cat4", "Đi chợ", "17-07-2024", "09:20", 300000L));
        expenseTrans.add(new Transaction("5", userID, "cat5", "Tiền điện", "16-07-2024", "18:45", 500000L));
        expenseTrans.add(new Transaction("6", userID, "cat6", "Tiền nước", "16-07-2024", "18:50", 200000L));
        expenseTrans.add(new Transaction("7", userID, "cat7", "Quần áo", "14-07-2024", "15:30", 800000L));

        TransactionAdapter expenseAdapter = new TransactionAdapter(getActivity(), R.layout.transaction_item, expenseTrans, getContext());
        expenseListView.setAdapter(expenseAdapter);
    }

    private void updatePieChart(PieChart pieChart, List<PieEntry> pieEntries, String type) {
        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        Description description = pieChart.getDescription();
        description.setText(type);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate();
    }
}