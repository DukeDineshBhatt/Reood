package com.ecommerce.ecommerce.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ecommerce.ecommerce.Adapters.SearchResultAdapter;
import com.ecommerce.ecommerce.R;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView = findViewById(R.id.search_result_recycler);
        SearchResultAdapter adapter = new SearchResultAdapter(this);
        recyclerView.setAdapter(adapter);
    }
}