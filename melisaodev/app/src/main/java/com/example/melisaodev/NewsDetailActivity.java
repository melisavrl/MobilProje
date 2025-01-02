package com.example.melisaodev;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

public class NewsDetailActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        String category = getIntent().getStringExtra("CATEGORY");
        Log.d("NewsDetailActivity", "Received category: " + category);

        if (category != null) {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            NewsItem newsItem = dbHelper.getNewsByCategory(category);
            
            if (newsItem != null) {
                ((ImageView) findViewById(R.id.detailImage)).setImageResource(newsItem.getImageResId());
                ((TextView) findViewById(R.id.detailTitle)).setText(newsItem.getCategory());
                ((TextView) findViewById(R.id.detailDescription)).setText(newsItem.getDescription());
                
                Log.d("NewsDetailActivity", "Description: " + newsItem.getDescription());
            } else {
                Log.e("NewsDetailActivity", "NewsItem is null for category: " + category);
            }
        } else {
            Log.e("NewsDetailActivity", "Category is null");
        }
    }
} 