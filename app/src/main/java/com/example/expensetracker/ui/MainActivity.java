package com.example.expensetracker.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.db.AppDatabase;
import com.example.expensetracker.db.Entry;
import com.example.expensetracker.util.ExcelExporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private EntryAdapter adapter;
    private EditText amountInput;
    private EditText noteInput;
    private EditText dateInput;
    private Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getInstance(this);

        amountInput = findViewById(R.id.amountInput);
        noteInput = findViewById(R.id.noteInput);
        dateInput = findViewById(R.id.dateInput);
        typeSpinner = findViewById(R.id.typeSpinner);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{"Debit", "Credit", "Paid"});
        typeSpinner.setAdapter(spinnerAdapter);

        // Default date = today
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.US)
                .format(Calendar.getInstance().getTime());
        dateInput.setText(today);

        RecyclerView rv = findViewById(R.id.entryList);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EntryAdapter();
        rv.setAdapter(adapter);

        Button addBtn = findViewById(R.id.addButton);
        addBtn.setOnClickListener(v -> addEntry());

        Button exportBtn = findViewById(R.id.exportButton);
        exportBtn.setOnClickListener(v -> exportExcel());

        refresh();
    }

    private void addEntry() {
        String amtStr = amountInput.getText().toString().trim();
        if (amtStr.isEmpty()) {
            Toast.makeText(this, "Enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }
        double amount;
        try {
            amount = Double.parseDouble(amtStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        String type = (String) typeSpinner.getSelectedItem();
        String date = dateInput.getText().toString().trim();
        String note = noteInput.getText().toString().trim();

        db.entryDao().insert(new Entry(type, amount, date, note));
        amountInput.setText("");
        noteInput.setText("");
        refresh();
        Toast.makeText(this, "Entry added", Toast.LENGTH_SHORT).show();
    }

    private void exportExcel() {
        try {
            List<Entry> all = db.entryDao().getAll();
            File f = ExcelExporter.export(this, all);
            Toast.makeText(this, "Exported to: " + f.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "Export failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void refresh() {
        adapter.setEntries(db.entryDao().getAll());
    }
}
