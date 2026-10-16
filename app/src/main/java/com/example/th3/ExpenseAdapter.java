package com.example.th3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class ExpenseAdapter extends ArrayAdapter<Expense> {
    private Context context;
    private ArrayList<Expense> expenses;

    public ExpenseAdapter(Context context, ArrayList<Expense> expenses) {
        super(context, R.layout.item_expense, expenses);
        this.context = context;
        this.expenses = expenses;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_expense, parent, false);
        }

        Expense expense = expenses.get(position);

        TextView tvName = convertView.findViewById(R.id.tvItemName);
        TextView tvAmount = convertView.findViewById(R.id.tvItemAmount);
        TextView tvCategory = convertView.findViewById(R.id.tvItemCategory);
        TextView tvDate = convertView.findViewById(R.id.tvItemDate);

        tvName.setText(expense.getName());
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvAmount.setText(nf.format(expense.getAmount()) + " đ");
        tvCategory.setText(expense.getCategory());
        tvDate.setText(expense.getDate());

        return convertView;
    }
}
