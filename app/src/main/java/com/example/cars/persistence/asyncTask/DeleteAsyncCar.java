package com.example.cars.persistence.asyncTask;

import android.os.AsyncTask;

import com.example.cars.model.Car;
import com.example.cars.persistence.CarDao;

public class DeleteAsyncCar extends AsyncTask<Car, Void, Void> {
    private CarDao mCarDao;

    public DeleteAsyncCar(CarDao mCarDao) {
        this.mCarDao = mCarDao;
    }

    @Override
    protected Void doInBackground(Car... cars) {
        mCarDao.deleteCar(cars);
        return null;
    }
}
