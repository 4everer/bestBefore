package com.example.bestbefore.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insertFood(FoodEntity... foodEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<FoodEntity> foodEntities);

    @Update
    void updateFood(FoodEntity... foodEntity);

    @Delete
    void deleteFood(FoodEntity... foodEntity);

    @Query("DELETE FROM dim_food")
    void deleteAll();

    @Query("SELECT * FROM dim_food ORDER BY id ASC")
    LiveData<List<FoodEntity>> getAllFood();

    @Query("SELECT * from dim_food LIMIT 1")
    FoodEntity[] getAnyFood();

    @Query("SELECT * FROM dim_food WHERE DATE(mBestBeforeDate) >= DATE('now')")
    LiveData<List<FoodEntity>> getGoodFood();

    @Query("SELECT * FROM dim_food WHERE DATE(mBestBeforeDate) < DATE('now')")
    LiveData<List<FoodEntity>> getBadFood();

    // Todo
    //    @Query("SELECT * FROM dim_food WHERE DATE(mBestBeforeDate) < DATE('now')")
    //    LiveData<List<FoodEntity>> getFoodExpireInDays(int days);
}
