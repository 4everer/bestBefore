package com.example.bestbefore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.bestbefore.FoodRepository;
import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private final FoodRepository mFoodRepository;
    private LiveData<List<FoodEntity>> mAllFood;

    public FoodViewModel(@NonNull Application application, FoodRepository foodRepository) {
        super(application);
        this.mFoodRepository = foodRepository;
        mAllFood = foodRepository.getAllFood();
    }

    public void insertFood(FoodEntity food) { mFoodRepository.insertFood(food); }
}
