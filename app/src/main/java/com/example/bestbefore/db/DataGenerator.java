package com.example.bestbefore.db;


import com.example.bestbefore.db.entity.FoodEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Generates data to pre-populate the database
 */
public class DataGenerator {

    private static final String[] FOOD_NAME = new String[]{
            "Milk", "Apples", "Beef", "Mango", "Orange Juice"};
    private static final LocalDate[] BEST_BEFORE_DATE = new LocalDate[]{
            LocalDate.now(), LocalDate.now().plusDays(1), LocalDate.now().plusDays(5), LocalDate.now().minusDays(1)};

    public static List<FoodEntity> generateFoods() {
        List<FoodEntity> foods = new ArrayList<>(FOOD_NAME.length * BEST_BEFORE_DATE.length);
        Random rnd = new Random();
        for (String s : FOOD_NAME) {
            for (LocalDate localDate : BEST_BEFORE_DATE) {
                FoodEntity food = new FoodEntity();
                food.setFoodName(s + " " + localDate);
                food.setQuantity(rnd.nextInt(240));
                food.setBestBeforeDate(localDate);
                foods.add(food);
            }
        }
        return foods;
    }
}
