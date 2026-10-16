package com.example.th3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ADD_EXPENSE = 1;
    private static final int REQUEST_DETAIL_EXPENSE = 2;

    public static ArrayList<Expense> expenseList = new ArrayList<>();
    private ExpenseAdapter adapter;
    private TextView tvTotalAmount;
    private ListView lvExpenses;
    private Button btnAddExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        lvExpenses = findViewById(R.id.lvExpenses);
        btnAddExpense = findViewById(R.id.btnAddExpense);

        if (expenseList.isEmpty()) {
            String today = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
            expenseList.add(new Expense("Ăn sáng", 30000, "Ăn uống", today, ""));
            expenseList.add(new Expense("Đổ xăng", 50000, "Đi lại", today, ""));
            expenseList.add(new Expense("Mua sách", 120000, "Học tập", today, ""));
        }

        adapter = new ExpenseAdapter(this, expenseList);
        lvExpenses.setAdapter(adapter);

        updateTotal();

        lvExpenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ExpenseDetailActivity.class);
                intent.putExtra("expense_index", position);
                startActivityForResult(intent, REQUEST_DETAIL_EXPENSE);
            }
        });

        btnAddExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddExpenseActivity.class);
                startActivityForResult(intent, REQUEST_ADD_EXPENSE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_ADD_EXPENSE && data != null) {
                Expense newExpense = (Expense) data.getSerializableExtra("new_expense");
                if (newExpense != null) {
                    expenseList.add(newExpense);
                }
            }
            adapter.notifyDataSetChanged();
            updateTotal();
        }
    }

    private void updateTotal() {
        long total = 0;
        for (Expense e : expenseList) {
            total += e.getAmount();
        }
        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvTotalAmount.setText(nf.format(total) + " đ");
    }
}
