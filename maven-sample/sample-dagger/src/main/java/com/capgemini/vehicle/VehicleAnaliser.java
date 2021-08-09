package com.capgemini.vehicle;


import com.capgemini.vehicle.Vehicle;

public class VehicleAnaliser {

    private final Vehicle vehicle;

    public VehicleAnaliser(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public String getVehicleType(){
        switch (vehicle.getWheels()) {
            case 1:
                return "monocycle";
            case 2:
            case 3:
                return "motorbike";
            case 4:
                return "car";
            default:
                return "unexpected";
        }
    }

}