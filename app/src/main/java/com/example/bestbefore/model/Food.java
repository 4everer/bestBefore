package com.example.bestbefore.model;

import java.time.LocalDate;

public interface Food {
    int getId();
    String getFoodName();
    int getQuantity();
    LocalDate getBestBeforeDate();
}
