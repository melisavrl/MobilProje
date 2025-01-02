package com.example.melisaodev

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CategoryListActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_list)
        
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        val categories = listOf(
            Category("Dünya", R.drawable.dunya),
            Category("Ekonomi", R.drawable.ekonomi),
            Category("Sanat", R.drawable.sanat),
            Category("Spor", R.drawable.spor),
            Category("Magazin", R.drawable.magazin)
        )
        
        recyclerView.adapter = CategoryAdapter(categories)
    }
}

data class Category(val name: String, val imageResId: Int) 