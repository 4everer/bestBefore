package com.example.bestbefore.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.bestbefore.AppExecutors;
import com.example.bestbefore.db.converter.DateConverter;
import com.example.bestbefore.db.dao.FoodDao;
import com.example.bestbefore.db.entity.FoodEntity;

import java.util.List;

@Database(entities = {FoodEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class FoodRoomDatabase extends RoomDatabase {

    public abstract FoodDao foodDao();

    private static FoodRoomDatabase INSTANCE;
    @VisibleForTesting
    public static final String DATABASE_NAME = "food_database";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static FoodRoomDatabase getInstance(final Context context, final AppExecutors executors) {
        if (INSTANCE == null) {
            synchronized (FoodRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    private void updateDatabaseCreated(Context applicationContext) {
        if (applicationContext.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private static FoodRoomDatabase buildDatabase(Context applicationContext, AppExecutors executors) {
        return Room.databaseBuilder(applicationContext, FoodRoomDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            // Generate the data for pre-population
                            FoodRoomDatabase database = FoodRoomDatabase.getInstance(applicationContext, executors);
                            List<FoodEntity> foodEntities = DataGenerator.generateFoods();
                            insertData(database, foodEntities);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
                // Wipes and rebuilds instead of migrating, for test and demo
                .fallbackToDestructiveMigration()
                .build();
    }

    private static void insertData(final FoodRoomDatabase database, final List<FoodEntity> foodEntities) {
        database.runInTransaction(() -> database.foodDao().insertAll(foodEntities));
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
