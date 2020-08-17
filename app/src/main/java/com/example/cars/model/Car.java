package com.example.cars.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Description")
    private String Description;

    @ColumnInfo(name = "Gas")
    private int Gas;

    @ColumnInfo(name = "color")
    private int color;

    @ColumnInfo(name = "picUrl")
    private int picUrl;

    @ColumnInfo(name = "carModel")
    private String carModel;

    public Car() {

    }

    public Car(String description, int gas, int color, int picUrl, String carModel) {
        Description = description;
        Gas = gas;
        this.color = color;
        this.picUrl = picUrl;
        this.carModel = carModel;
    }

    protected Car(Parcel in) {
        id = in.readInt();
        Description = in.readString();
        Gas = in.readInt();
        color = in.readInt();
        picUrl = in.readInt();
        carModel = in.readString();
    }

    public static final Creator<Car> CREATOR = new Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel in) {
            return new Car(in);
        }

        @Override
        public Car[] newArray(int size) {
            return new Car[size];
        }
    };

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getGas() {
        return Gas;
    }

    public void setGas(int gas) {
        Gas = gas;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(int picUrl) {
        this.picUrl = picUrl;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Car{" +
                "Description='" + Description + '\'' +
                ", Gas=" + Gas +
                ", color=" + color +
                ", carModel='" + carModel + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(Description);
        parcel.writeInt(Gas);
        parcel.writeInt(color);
        parcel.writeInt(picUrl);
        parcel.writeString(carModel);
    }
}
