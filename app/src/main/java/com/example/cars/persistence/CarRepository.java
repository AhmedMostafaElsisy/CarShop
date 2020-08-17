package com.example.cars.persistence;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.cars.model.Car;
import com.example.cars.persistence.asyncTask.InsertAsyncTask;
import com.example.cars.persistence.asyncTask.DeleteAsyncCar;
import com.example.cars.persistence.asyncTask.UpdateAsyncTask;

import java.util.List;

public class CarRepository {
    private CarDataBase carDataBase;

    public CarRepository(Context context) {
        this.carDataBase = CarDataBase.getInstance(context);
    }

    public void insertCarTask(Car car) {
        new InsertAsyncTask(carDataBase.getCarDao()).execute(car);
    }

    public void updateCarTask(Car car) {
        new UpdateAsyncTask(carDataBase.getCarDao()).execute(car);
    }

    public void deleteCarTask(Car car) {
        new DeleteAsyncCar(carDataBase.getCarDao()).execute(car);
    }

    public LiveData<List<Car>> retrieveCarsTask() {
        return carDataBase.getCarDao().getCar();
    }
}
