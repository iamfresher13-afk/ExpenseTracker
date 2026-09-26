# Expense Tracker (Android, Native Java)

A lightweight Android app to record daily **Debit / Credit / Paid** entries, each with an amount, date, and optional note. Data is stored locally in a Room (SQLite) database and can be exported on demand to a real Excel (.xlsx) file via Apache POI.

## Features
- Add entries with type (Debit/Credit/Paid), amount, date (yyyy-MM-dd), and optional note
- Entries stored locally in a Room database
- On-demand "Export to Excel" button -> writes expenses_export.xlsx
- Simple, single-screen UI

## Requirements
- Android Studio (Hedgehog or newer)
- Android SDK 34
- A device or emulator running Android 7.0 (API 24) or higher

## How to open and run
1. Open Android Studio -> **File > Open** -> select the `ExpenseTracker` folder.
2. Let Gradle sync finish (it downloads Room, Material, and Apache POI).
3. Create an emulator (**Device Manager > Create Device**, e.g. Pixel 6, API 34) or plug in a physical phone with USB debugging enabled.
4. Press **Run** (green arrow).

## How to test the app manually
1. **Add entries**: pick a type, enter an amount (e.g. 250.00), adjust the date, tap **Add Entry**. The entry appears in the list below.
2. **Persistence**: close and reopen the app -> your entries are still there (proves the database works).
3. **Export**: tap **Export to Excel**. A toast shows the saved path:
   `Android/data/com.example.expensetracker/files/Documents/expenses_export.xlsx`
4. **Verify the Excel file**:
   - On the emulator: **Device Explorer** in Android Studio -> navigate to the path above -> right-click -> Save As -> open in Excel.
   - On a phone: use a file manager or connect via USB and copy the file to your PC.
   - Confirm columns: ID, Type, Amount, Date, Note, and one row per entry.

## Where the data lives
- Database: internal app storage (`expense.db`)
- Excel export: app-specific external Documents folder (no storage permission needed)

## Notes
- Room queries run on the main thread here (`allowMainThreadQueries`) to keep the app lightweight. For larger datasets, move DB calls to a background thread.
- The date field is a free-text `yyyy-MM-dd` string for simplicity; you can swap in a DatePickerDialog if you prefer a calendar picker.
