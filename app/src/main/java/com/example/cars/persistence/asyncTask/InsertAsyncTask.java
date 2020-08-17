package com.example.cars.persistence.asyncTask;

import android.os.AsyncTask;

import com.example.cars.model.Car;
import com.example.cars.persistence.CarDao;

public class InsertAsyncTask extends AsyncTask<Car, Void, Void> {
    private CarDao mCarDao;

    public InsertAsyncTask(CarDao dao) {
        this.mCarDao = dao;
    }

    @Override
    protected Void doInBackground(Car... cars) {

        mCarDao.insertCar(cars);
        return null;
    }
}
