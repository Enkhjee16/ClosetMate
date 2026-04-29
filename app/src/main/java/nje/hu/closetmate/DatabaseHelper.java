// DatabaseHelper.java
package nje.hu.closetmate;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import android.database.Cursor;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "closetmate.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_CLOTHING = "clothing_items";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createClothingTable = "CREATE TABLE " + TABLE_CLOTHING + " (" +
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

        db.execSQL(createClothingTable);
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
        values.put("created_at", getCurrentDate());

        long result = db.insert(TABLE_CLOTHING, null, values);
        db.close();

        return result != -1;
    }

    private String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return formatter.format(new Date());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLOTHING);
        onCreate(db);
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
}