// DatabaseHelper.java
package nje.hu.closetmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "closetmate.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_CLOTHING = "clothing_items";
    public static final String TABLE_PLANS = "outfit_plans";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createClothingTable = "CREATE TABLE IF NOT EXISTS " + TABLE_CLOTHING + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "image_uri TEXT, " +
                "category TEXT, " +
                "color TEXT, " +
                "season TEXT, " +
                "style TEXT, " +
                "occasion TEXT, " +
                "wear_count INTEGER DEFAULT 0, " +
                "created_at TEXT" +
                ");";

        String createPlansTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PLANS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "planned_date TEXT NOT NULL, " +
                "outfit_name TEXT NOT NULL, " +
                "notes TEXT" +
                ");";

        db.execSQL(createClothingTable);
        db.execSQL(createPlansTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String createPlansTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PLANS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "planned_date TEXT NOT NULL, " +
                "outfit_name TEXT NOT NULL, " +
                "notes TEXT" +
                ");";

        db.execSQL(createPlansTable);
    }

    public boolean insertClothingItem(String name, String imageUri, String category,
                                      String color, String season, String style,
                                      String occasion) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("image_uri", imageUri);
        values.put("category", category);
        values.put("color", color);
        values.put("season", season);
        values.put("style", style);
        values.put("occasion", occasion);
        values.put("wear_count", 0);
        values.put("created_at", getCurrentDateTime());

        long result = db.insert(TABLE_CLOTHING, null, values);
        db.close();

        return result != -1;
    }

    public ArrayList<ClothingItem> getAllClothingItems() {
        ArrayList<ClothingItem> clothingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_CLOTHING + " ORDER BY id DESC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                ClothingItem item = new ClothingItem(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("image_uri")),
                        cursor.getString(cursor.getColumnIndexOrThrow("category")),
                        cursor.getString(cursor.getColumnIndexOrThrow("color")),
                        cursor.getString(cursor.getColumnIndexOrThrow("season")),
                        cursor.getString(cursor.getColumnIndexOrThrow("style")),
                        cursor.getString(cursor.getColumnIndexOrThrow("occasion")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("wear_count")),
                        cursor.getString(cursor.getColumnIndexOrThrow("created_at"))
                );

                clothingList.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return clothingList;
    }

    public boolean insertOutfitPlan(String plannedDate, String outfitName, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("planned_date", plannedDate);
        values.put("outfit_name", outfitName);
        values.put("notes", notes);

        long result = db.insert(TABLE_PLANS, null, values);
        db.close();

        return result != -1;
    }

    public int getClothingItemCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_CLOTHING,
                null
        );

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count;
    }

    public int getOutfitPlanCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM " + TABLE_PLANS,
                null
        );

        int count = 0;

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        }

        cursor.close();
        db.close();

        return count;
    }

    public Map<String, Integer> getCategoryCounts() {
        Map<String, Integer> categoryCounts = new HashMap<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT category, COUNT(*) FROM " + TABLE_CLOTHING + " GROUP BY category",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                String category = cursor.getString(0);
                int count = cursor.getInt(1);

                categoryCounts.put(category, count);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return categoryCounts;
    }

    private String getCurrentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
        );

        return formatter.format(new Date());
    }
}