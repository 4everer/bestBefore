package com.example.bestbefore.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.bestbefore.model.Food;

import java.time.LocalDate;

@Entity(tableName = "dim_food")
public class FoodEntity implements Food {

    @PrimaryKey(autoGenerate = true) private int id;
    @ColumnInfo private String mFoodName;
    @ColumnInfo private int mQuantity;
    @ColumnInfo private LocalDate mBestBeforeDate;

    public int getId() {return id;}

    @Override
    public String getFoodName(){return this.mFoodName;}

    public void setFoodName(String foodName){this.mFoodName = foodName;}

    @Override
    public LocalDate getBestBeforeDate(){return this.mBestBeforeDate;}

    public void setBestBeforeDate(LocalDate bestBeforeDate){this.mBestBeforeDate = bestBeforeDate;}

    @Override
    public int getQuantity() {return this.mQuantity;}

    public void setQuantity(int quantity) {this.mQuantity = quantity;}


    public FoodEntity(){}

    @Ignore
    public FoodEntity(int id, @NonNull String foodName, int quantity, LocalDate bestBeforeDate) {
        this.id = id;
        this.mFoodName = foodName;
        this.mQuantity = quantity;
        this.mBestBeforeDate = bestBeforeDate;
    }

    public FoodEntity(Food food) {
        this.id = food.getId();
        this.mFoodName = food.getFoodName();
        this.mQuantity = food.getQuantity();
        this.mBestBeforeDate = food.getBestBeforeDate();
    }
}