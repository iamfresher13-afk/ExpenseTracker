package com.example.expensetracker.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "entries")
public class Entry {
    @PrimaryKey(autoGenerate = true)
    public long id;

    // Type: "Debit", "Credit", or "Paid"
    @NonNull
    public String type;

    public double amount;

    // Stored as yyyy-MM-dd
    @NonNull
    public String date;

    public String note;

    public Entry(@NonNull String type, double amount, @NonNull String date, String note) {
        this.type = type;
        this.amount = amount;
        this.date = date;
        this.note = note;
    }
}
