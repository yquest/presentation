package com.capgemini.vehicle.module;

import com.capgemini.vehicle.Brand;
import com.capgemini.vehicle.Vehicle;

public class VehiclesModuleTest extends VehiclesModule{

    private Brand brand;
    private int wheels;

    public void setWheels(int wheels) {
        this.wheels = wheels;
    }

    @Override
    public Brand getBrand() {
        return brand;
    }

    public void setBrand(String brandName) {
        this.brand = new Brand(brandName);
    }

    @Override
    public Vehicle getVehicle(Brand brand) {
        return new GenericVehicle(brand, wheels);
    }
}
