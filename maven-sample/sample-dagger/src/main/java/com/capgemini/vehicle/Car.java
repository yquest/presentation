package com.capgemini.vehicle;

import javax.inject.Inject;

public class Car  implements Vehicle{
    private Brand brand;

    @Inject
    public Car(Brand brand) {
        this.brand = brand;
    }

    @Override
    public int getWheels() {
        return 4;
    }

    public Brand getBrand() {
        return brand;
    }
}
