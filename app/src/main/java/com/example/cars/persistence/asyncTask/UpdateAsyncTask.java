package com.example.cars.persistence.asyncTask;

import android.os.AsyncTask;

import com.example.cars.model.Car;
import com.example.cars.persistence.CarDao;

public class UpdateAsyncTask extends AsyncTask<Car, Void, Void> {
    private CarDao mCarDao;

    public UpdateAsyncTask(CarDao mCarDao) {
        this.mCarDao = mCarDao;
    }

    @Override
    protected Void doInBackground(Car... cars) {
        mCarDao.updateCar(cars);
        return null;
    }
}
