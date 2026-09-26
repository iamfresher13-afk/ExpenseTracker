package com.example.expensetracker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.expensetracker.R;
import com.example.expensetracker.db.Entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.VH> {

    private List<Entry> entries = new ArrayList<>();

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_entry, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Entry e = entries.get(position);
        holder.title.setText(String.format(Locale.US, "%s  %.2f", e.type, e.amount));
        holder.subtitle.setText(e.date + (e.note == null || e.note.isEmpty() ? "" : "  ·  " + e.note));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView title;
        TextView subtitle;
        VH(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.itemTitle);
            subtitle = itemView.findViewById(R.id.itemSubtitle);
        }
    }
}
