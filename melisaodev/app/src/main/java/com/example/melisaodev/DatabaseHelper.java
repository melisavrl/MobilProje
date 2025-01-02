package com.example.melisaodev;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NewsDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "news";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_IMAGE = "image";
    private static final String COLUMN_DESCRIPTION = "description";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_CATEGORY + " TEXT, " +
                COLUMN_IMAGE + " INTEGER, " +
                COLUMN_DESCRIPTION + " TEXT)";
        
        db.execSQL(createTable);
        insertInitialData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        NewsItem[] newsData = {
            new NewsItem("Dünya", R.drawable.listdunya, 
                "Son Dakika: Dünya genelinde önemli gelişmeler yaşanıyor!\n\n" +
                "• İklim değişikliği ile mücadele kapsamında yeni anlaşmalar imzalandı\n" +
                "• BM Genel Kurulu'nda küresel sorunlar masaya yatırıldı\n" +
                "• Uzay araştırmalarında çığır açan keşifler yapıldı\n" +
                "• Uluslararası barış görüşmeleri devam ediyor\n\n" +
                "Dünya gündeminden seçilen önemli başlıklar ve detaylı analizler için haberin devamı..."),
            // Diğer kategoriler için benzer şekilde devam eder...
        };

        for (NewsItem news : newsData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_CATEGORY, news.getCategory());
            values.put(COLUMN_IMAGE, news.getImageResId());
            values.put(COLUMN_DESCRIPTION, news.getDescription());
            db.insert(TABLE_NAME, null, values);
        }
    }

    public NewsItem getNewsByCategory(String category) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
            TABLE_NAME,
            new String[]{COLUMN_CATEGORY, COLUMN_IMAGE, COLUMN_DESCRIPTION},
            COLUMN_CATEGORY + " = ?",
            new String[]{category},
            null, null, null
        );

        Log.d("DatabaseHelper", "Searching for category: " + category);
        
        NewsItem newsItem = null;
        if (cursor.moveToFirst()) {
            int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            Log.d("DatabaseHelper", "Found description: " + description);
            newsItem = new NewsItem(category, imageResId, description);
        } else {
            Log.e("DatabaseHelper", "No data found for category: " + category);
        }
        
        cursor.close();
        return newsItem;
    }
} 