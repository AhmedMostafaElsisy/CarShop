package com.example.cars.persistence;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cars.model.Car;

@Database(entities = {Car.class}, version = 1)
public abstract class CarDataBase extends RoomDatabase {

    public static final String DATABASE_NAME = "car_db";
    private static CarDataBase instance;

    static CarDataBase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CarDataBase.class,
                    DATABASE_NAME
            ).build();
        }
        return instance;
    }

    public abstract CarDao getCarDao();
}
