package com.capgemini.vehicle.module;

import com.capgemini.vehicle.VehicleAnaliser;
import com.capgemini.vehicle.DaggerVehiclesComponent;
import com.capgemini.vehicle.Vehicle;
import org.junit.Assert;
import org.junit.Test;

public class VehicleTest {
    @Test
    public void testDaggerVehicle() {
        Vehicle vehicle = DaggerVehiclesComponent.builder()
                .build()
                .buildVehicle();
        VehicleAnaliser vehicleAnaliser = new VehicleAnaliser(vehicle);
        Assert.assertEquals("car", vehicleAnaliser.getVehicleType());

        String brandName = "kawasaki";
        int wheels = 2;
        VehiclesModuleTest vehiclesModuleTest = new VehiclesModuleTest();
        vehiclesModuleTest.setBrand(brandName);
        vehiclesModuleTest.setWheels(wheels);

        vehicle = DaggerVehiclesComponent.builder()
                .vehiclesModule(vehiclesModuleTest)
                .build()
                .buildVehicle();
        vehicleAnaliser = new VehicleAnaliser(vehicle);

        Assert.assertEquals(brandName, vehicle.getBrand().getName());
        Assert.assertEquals(wheels, vehicle.getWheels());
        Assert.assertEquals("motorbike", vehicleAnaliser.getVehicleType());

        wheels = 5;
        vehiclesModuleTest.setWheels(wheels);
        vehicle = DaggerVehiclesComponent.builder()
                .vehiclesModule(vehiclesModuleTest)
                .build()
                .buildVehicle();
        vehicleAnaliser = new VehicleAnaliser(vehicle);
        Assert.assertEquals("unexpected", vehicleAnaliser.getVehicleType());
    }
}
