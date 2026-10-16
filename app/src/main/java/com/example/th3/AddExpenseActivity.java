package com.example.th3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddExpenseActivity extends AppCompatActivity {

    private EditText etExpenseName, etAmount, etNote;
    private Spinner spinnerCategory;
    private DatePicker datePicker;
    private Button btnSaveExpense;

    private String[] categories = {"Ăn uống", "Đi lại", "Học tập", "Giải trí", "Khác"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        etExpenseName = findViewById(R.id.etExpenseName);
        etAmount = findViewById(R.id.etAmount);
        etNote = findViewById(R.id.etNote);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        datePicker = findViewById(R.id.datePicker);
        btnSaveExpense = findViewById(R.id.btnSaveExpense);

        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        btnSaveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etExpenseName.getText().toString().trim();
                String amountStr = etAmount.getText().toString().trim();
                String note = etNote.getText().toString().trim();
                String category = spinnerCategory.getSelectedItem().toString();

                if (name.isEmpty()) {
                    etExpenseName.setError("Tên khoản chi không được để trống");
                    etExpenseName.requestFocus();
                    Toast.makeText(AddExpenseActivity.this, "Tên khoản chi không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (amountStr.isEmpty()) {
                    etAmount.setError("Số tiền không được để trống");
                    etAmount.requestFocus();
                    Toast.makeText(AddExpenseActivity.this, "Số tiền không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }

                long amount;
                try {
                    amount = Long.parseLong(amountStr);
                } catch (NumberFormatException e) {
                    etAmount.setError("Số tiền không hợp lệ");
                    etAmount.requestFocus();
                    Toast.makeText(AddExpenseActivity.this, "Số tiền không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (amount <= 0) {
                    etAmount.setError("Số tiền phải lớn hơn 0");
                    etAmount.requestFocus();
                    Toast.makeText(AddExpenseActivity.this, "Số tiền phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth() + 1;
                int year = datePicker.getYear();
                String date = String.format("%02d/%02d/%04d", day, month, year);

                Expense expense = new Expense(name, amount, category, date, note);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("new_expense", expense);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
