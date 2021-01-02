package com.example.bestbefore.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestbefore.R;
import com.example.bestbefore.db.entity.FoodEntity;
import com.example.bestbefore.viewmodel.FoodViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel mFoodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FoodListAdapter adapter = new FoodListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mFoodViewModel = ViewModelProviders.of(this).get(FoodViewModel.class);

        mFoodViewModel.getAllFood().observe(this, new Observer<List<FoodEntity>>() {
            @Override
            public void onChanged(@Nullable final List<FoodEntity> foods) {
                // Update the cached copy of the words in the adapter.
                adapter.setFoods(foods);
            }
        });
    }
}