package com.example.bestbefore;

import android.app.Application;

import com.example.bestbefore.db.FoodRoomDatabase;

public class BestBefore extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public FoodRoomDatabase getDatabase() {
        return FoodRoomDatabase.getInstance(this, mAppExecutors);
    }

    public FoodRepository getRepository() {
        return FoodRepository.getInstance(getDatabase());
    }
}
