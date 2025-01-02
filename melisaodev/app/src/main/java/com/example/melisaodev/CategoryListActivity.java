package com.example.melisaodev;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Arrays;
import java.util.List;

public class CategoryListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);
        
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        List<Category> categories = Arrays.asList(
            new Category("Dünya", R.drawable.dunya),
            new Category("Ekonomi", R.drawable.ekonomi),
            new Category("Sanat", R.drawable.sanat),
            new Category("Spor", R.drawable.spor),
            new Category("Magazin", R.drawable.magazin)
        );
        
        recyclerView.setAdapter(new CategoryAdapter(categories));
    }
} 