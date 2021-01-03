package com.example.bestbefore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.bestbefore.db.FoodRoomDatabase;
import com.example.bestbefore.db.dao.FoodDao;
import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

public class FoodRepository {
    private static AppExecutors mAppExecutors;
    private final FoodRoomDatabase mDatabase;

    private static FoodRepository sInstance;
    private FoodDao mFoodDao;
    private MediatorLiveData<List<FoodEntity>> mObeservableFood;

    public FoodRepository(AppExecutors appExecutors, FoodRoomDatabase database) {
        mAppExecutors = appExecutors;
        mDatabase = database;

        mFoodDao = mDatabase.foodDao();
        mObeservableFood = new MediatorLiveData<>();

        mObeservableFood.addSource(mFoodDao.getAllFood(),
                foodEntities -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mObeservableFood.postValue(foodEntities);
                    }
                });
    }

    public static FoodRepository getInstance(AppExecutors appExecutors, final FoodRoomDatabase database) {
        if (sInstance == null) {
            synchronized (FoodRepository.class) {
                if (sInstance == null) {
                    sInstance = new FoodRepository(appExecutors, database);
                }
            }
        }
        return sInstance;
    }


    public LiveData<List<FoodEntity>> getAllFood() {
        return mObeservableFood;
    }

    public void insertFood (FoodEntity food) {
        mAppExecutors.diskIO().execute(() -> {
            mFoodDao.insertFood(food);
        });
    }

    public void deleteAll() {
        mAppExecutors.diskIO().execute(() -> {
            mFoodDao.deleteAll();
        });
    }

    public void deleteFood(FoodEntity food) {
        mAppExecutors.diskIO().execute(() -> {
            mFoodDao.deleteFood(food);
        });
    }
}
