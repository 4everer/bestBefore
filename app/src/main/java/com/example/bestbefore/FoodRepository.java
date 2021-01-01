package com.example.bestbefore;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.bestbefore.db.FoodRoomDatabase;
import com.example.bestbefore.db.dao.FoodDao;
import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

public class FoodRepository {
    final private AppExecutors mAppExecutors;
    private FoodDao mFoodDao;
    private MediatorLiveData<List<FoodEntity>> mObeservableFood;

    public FoodRepository(Application application, AppExecutors appExecutors) {

        this.mAppExecutors = appExecutors;

        FoodRoomDatabase mDatabase = FoodRoomDatabase.getInstance(application, appExecutors);
        mFoodDao = mDatabase.foodDao();
        mObeservableFood = new MediatorLiveData<>();

        mObeservableFood.addSource(mFoodDao.getAllFood(),
                foodEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObeservableFood.postValue(foodEntities);
                    }
                });
    }


    public LiveData<List<FoodEntity>> getAllFood() {
        return mObeservableFood;
    }

    public void insertFood (FoodEntity food) {
        mAppExecutors.diskIO().execute(() -> {
            mFoodDao.insertFood(food);
        });
    }
}
