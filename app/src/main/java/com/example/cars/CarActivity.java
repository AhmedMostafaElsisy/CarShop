package com.example.cars;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.cars.model.Car;
import com.example.cars.persistence.CarRepository;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;


public class CarActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int EDIT_MODE_ENABLE = 1;
    private static final int EDIT_MODE_DISABLE = 0;
    private static final int NEW_CAR = 0;
    private static final int OLD_CAR = 1;
    private int mMode;
    private int stateCar;
    private Car mInitialCar;
    private TextInputEditText carModel, carGAS, carColor, carDesc;
    private ImageView imageView;
    private FloatingActionButton FAB;
    private BottomAppBar toolbar;
    private LinearLayout bottom_sheet;
    private BottomSheetBehavior sheetBehavior;
    private CarRepository repository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        repository = new CarRepository(this);
        initTextView();
        getIncomeIntent();
        setDataToFiled();
        FAB.setOnClickListener(this);
    }

    private void getIncomeIntent() {
        if (getIntent().hasExtra("selectedCar")) {
            mInitialCar = getIntent().getParcelableExtra("selectedCar");
            mMode = EDIT_MODE_DISABLE;
            stateCar = OLD_CAR;
        } else {
            mInitialCar = new Car();
            setDefoultData();
            mMode = EDIT_MODE_ENABLE;
            enableEditModeOnView();
            stateCar = NEW_CAR;
        }
    }

    private void setDefoultData() {
        mInitialCar.setCarModel("test model");
        mInitialCar.setColor(25);
        mInitialCar.setGas(25);
        mInitialCar.setDescription("test model car");
        mInitialCar.setPicUrl(R.drawable.ic_launcher_background);

    }

    private void initTextView() {
        carModel = findViewById(R.id.CarModelTextField);
        carGAS = findViewById(R.id.CarGasTextField);
        carColor = findViewById(R.id.CarColorTextField);
        carDesc = findViewById(R.id.CarDescTextField);
        imageView = findViewById(R.id.carImgDetails);
        toolbar = findViewById(R.id.barDetails);
        bottom_sheet = findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        setSupportActionBar(toolbar);
        FAB = findViewById(R.id.fabEdit);
        FAB.setColorFilter(Color.WHITE);
        bottomSheetAction();
    }

    private void bottomSheetAction() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                LinearLayout saveChange = findViewById(R.id.save_layout);
                LinearLayout deleteCar = findViewById(R.id.delete_layout);
                saveChange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CarActivity.this, "saved", Toast.LENGTH_SHORT).show();
                        getDataFromFiled();
                        if (stateCar == NEW_CAR) {
                            repository.insertCarTask(mInitialCar);
                            //   backToMainView();

                        }
                        if (stateCar == OLD_CAR) {
                            repository.updateCarTask(mInitialCar);
                            //   backToMainView();

                        }
                    }
                });
                deleteCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(CarActivity.this, "delete", Toast.LENGTH_SHORT).show();
                        repository.deleteCarTask(mInitialCar);
                        backToMainView();
                    }
                });
            }
        });
    }

    private void backToMainView() {
        Intent intent = new Intent(CarActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setDataToFiled() {
        carModel.setText(mInitialCar.getCarModel());
        carGAS.setText(String.valueOf(mInitialCar.getGas()));
        carColor.setText(String.valueOf(mInitialCar.getColor()));
        carDesc.setText(mInitialCar.getDescription());
        Glide.with(this)
                .load(mInitialCar.getPicUrl())
                .into(imageView);

    }

    private void getDataFromFiled() {
        String mDescription, mCarModel;
        int mGas, mColor, mPicUrl;
        mCarModel = carModel.getText().toString().trim();
        mDescription = carDesc.getText().toString().trim();
        mGas = Integer.parseInt(carGAS.getText().toString().trim());
        mColor = Integer.parseInt(carColor.getText().toString().trim());
        mInitialCar.setCarModel(mCarModel);
        mInitialCar.setDescription(mDescription);
        mInitialCar.setGas(mGas);
        mInitialCar.setColor(mColor);
    }

    private void enableEditMode() {
        carModel.setEnabled(true);
        carGAS.setEnabled(true);
        carColor.setEnabled(true);
        carDesc.setEnabled(true);
    }

    private void disableEditMode() {
        carModel.setEnabled(false);
        carGAS.setEnabled(false);
        carColor.setEnabled(false);
        carDesc.setEnabled(false);
    }

    private void enableEditModeOnView() {
        FAB.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check));
        enableEditMode();
    }

    private void disableEditModeOnView() {
        FAB.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_edit));
        getDataFromFiled();
        disableEditMode();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabEdit:
                if (mMode == EDIT_MODE_DISABLE) {
                    enableEditModeOnView();
                    mMode = EDIT_MODE_ENABLE;
                } else if (mMode == EDIT_MODE_ENABLE) {
                    if (stateCar == NEW_CAR) {
                        getDataFromFiled();
                        repository.insertCarTask(mInitialCar);
                        backToMainView();

                    }
                    if (stateCar == OLD_CAR) {
                        getDataFromFiled();
                        repository.updateCarTask(mInitialCar);
                        backToMainView();

                    }
                    disableEditModeOnView();
                    mMode = EDIT_MODE_DISABLE;
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        backToMainView();
        super.onBackPressed();
    }
}
