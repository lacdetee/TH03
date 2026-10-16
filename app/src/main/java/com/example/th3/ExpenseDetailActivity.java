package com.example.th3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ExpenseDetailActivity extends AppCompatActivity {

    private TextView tvDetailName, tvDetailAmount, tvDetailCategory, tvDetailDate, tvDetailNote;
    private Button btnBack, btnDelete;
    private int expenseIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);

        tvDetailName = findViewById(R.id.tvDetailName);
        tvDetailAmount = findViewById(R.id.tvDetailAmount);
        tvDetailCategory = findViewById(R.id.tvDetailCategory);
        tvDetailDate = findViewById(R.id.tvDetailDate);
        tvDetailNote = findViewById(R.id.tvDetailNote);
        btnBack = findViewById(R.id.btnBack);
        btnDelete = findViewById(R.id.btnDelete);

        expenseIndex = getIntent().getIntExtra("expense_index", -1);

        if (expenseIndex >= 0 && expenseIndex < MainActivity.expenseList.size()) {
            Expense expense = MainActivity.expenseList.get(expenseIndex);

            tvDetailName.setText(expense.getName());
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            tvDetailAmount.setText(nf.format(expense.getAmount()) + " đ");
            tvDetailCategory.setText(expense.getCategory());
            tvDetailDate.setText(expense.getDate());

            String note = expense.getNote();
            tvDetailNote.setText((note != null && !note.isEmpty()) ? note : "Không có ghi chú");
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (expenseIndex >= 0 && expenseIndex < MainActivity.expenseList.size()) {
                    MainActivity.expenseList.remove(expenseIndex);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
