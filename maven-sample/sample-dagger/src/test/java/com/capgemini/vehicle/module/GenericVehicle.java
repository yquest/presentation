package com.capgemini.vehicle.module;

import com.capgemini.vehicle.Brand;
import com.capgemini.vehicle.Vehicle;

public class GenericVehicle implements Vehicle {

    private final Brand brand;
    private final int wheels;

    public GenericVehicle(Brand brand, int wheels) {
        this.brand = brand;
        this.wheels = wheels;
    }

    @Override
    public int getWheels() {
        return wheels;
    }

    @Override
    public Brand getBrand() {
        return brand;
    }
}
