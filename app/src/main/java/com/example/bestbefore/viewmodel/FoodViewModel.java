package com.example.bestbefore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.bestbefore.BestBefore;
import com.example.bestbefore.FoodRepository;
import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

public class FoodViewModel extends AndroidViewModel {

    private final FoodRepository mFoodRepository;
    private LiveData<List<FoodEntity>> mAllFood;

    public FoodViewModel(@NonNull Application application) {
        super(application);
        mFoodRepository = ((BestBefore) application).getRepository();
        mAllFood = mFoodRepository.getAllFood();
    }

    public LiveData<List<FoodEntity>> getAllFood() { return mAllFood; }
    public void insertFood(FoodEntity food) { mFoodRepository.insertFood(food); }

    public void deleteAll() { mFoodRepository.deleteAll(); }

    public void deleteFood(FoodEntity food) {mFoodRepository.deleteFood(food);}

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. 
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final FoodRepository mRepository;

        public Factory(@NonNull Application application) {
            mApplication = application;
            mRepository = ((BestBefore) application).getRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new FoodViewModel(mApplication);
        }
    }
}
