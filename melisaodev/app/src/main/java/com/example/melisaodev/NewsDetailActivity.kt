package com.example.melisaodev

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class NewsDetailActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_detail)

        val category = intent.getStringExtra("CATEGORY")
        Log.d("NewsDetailActivity", "Received category: $category") // Debug için log

        if (category != null) {
            val dbHelper = DatabaseHelper(this)
            val newsItem = dbHelper.getNewsByCategory(category)
            
            if (newsItem != null) {
                findViewById<ImageView>(R.id.detailImage).setImageResource(newsItem.imageResId)
                findViewById<TextView>(R.id.detailTitle).text = newsItem.category
                findViewById<TextView>(R.id.detailDescription).text = newsItem.description
                
                Log.d("NewsDetailActivity", "Description: ${newsItem.description}") // Debug için log
            } else {
                Log.e("NewsDetailActivity", "NewsItem is null for category: $category")
            }
        } else {
            Log.e("NewsDetailActivity", "Category is null")
        }
    }
} 