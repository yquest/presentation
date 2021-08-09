package com.capgemini.vehicle.module;

import com.capgemini.vehicle.Brand;
import com.capgemini.vehicle.Car;
import com.capgemini.vehicle.Vehicle;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class VehiclesModule {
    @Provides
    @Singleton
    public Vehicle getVehicle(Brand brand){
        return new Car(brand);
    }

    @Provides
    public Brand getBrand(){
        return new Brand("Mercedes");
    }
}
