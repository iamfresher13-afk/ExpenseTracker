package com.example.expensetracker.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
    @Insert
    long insert(Entry entry);

    @Delete
    void delete(Entry entry);

    @Query("SELECT * FROM entries ORDER BY date DESC, id DESC")
    List<Entry> getAll();
}
