package com.capgemini.vehicle;

import com.capgemini.vehicle.module.VehiclesModule;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = VehiclesModule.class)
public interface VehiclesComponent {
    Vehicle buildVehicle();
}
