package com.example.cars.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cars.model.Car;

import java.util.List;

@Dao
public interface CarDao {
    @Insert
    long[] insertCar(Car[] notes);

    @Query("SELECT * FROM cars")
    LiveData<List<Car>> getCar();

    @Delete
    int deleteCar(Car[] cars);

    @Update
    int updateCar(Car[] cars);
}
