package com.example.bestbefore.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bestbefore.R;
import com.example.bestbefore.db.entity.FoodEntity;
import com.example.bestbefore.viewmodel.FoodViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FoodViewModel mFoodViewModel;
    public static final int NEW_FOOD_ACTIVITY_REQUEST_CODE = 1;

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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewFoodActivity.class);
                startActivityForResult(intent, NEW_FOOD_ACTIVITY_REQUEST_CODE);
            }
        });

        // Add the functionality to swipe items in the
        // recycler view to delete that item
        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView,
                                          @NonNull RecyclerView.ViewHolder viewHolder,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                         int direction) {
                        int position = viewHolder.getAdapterPosition();
                        FoodEntity myFood = adapter.getFoodAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myFood.getFoodName(), Toast.LENGTH_LONG).show();

                        // Delete the word
                        mFoodViewModel.deleteFood(myFood);
                    }
                });

        helper.attachToRecyclerView(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // The options menu has a single item "Clear all data now"
    // that deletes all the entries in the database.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.clear_data) {
            // Add a toast just for confirmation
            Toast.makeText(this, R.string.clear_data_toast_text, Toast.LENGTH_LONG).show();

            // Delete the existing data.
            mFoodViewModel.deleteAll();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == NEW_FOOD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = intent.getExtras();
            String foodName = extras.getString("FOOD_NAME");
            int quantity = extras.getInt("QUANTITY");
            LocalDate bestBeforeDate = LocalDate.parse(extras.getString("BEST_BEFORE_DATE"), DateTimeFormatter.ISO_LOCAL_DATE);
            FoodEntity food = new FoodEntity(foodName, quantity, bestBeforeDate);
            mFoodViewModel.insertFood(food);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }


}