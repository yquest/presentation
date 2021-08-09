package com.capgemini.pearson;

import com.capgemini.annotations.Delegate;
import com.capgemini.annotations.DelegateClass;
import com.capgemini.annotations.Log;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@DelegateClass
public class Pearson {
    private final String name;
    private final Map<LocalDate, Integer> weighings;


    public Pearson() {
        this.name = null;
        this.weighings = new HashMap<>();
    }

    @Delegate
    public Pearson(String name) {
        this.name = name;
        this.weighings = new HashMap<>();
    }

    @Log
    public Pearson(String name, Map<LocalDate, Integer> weighings) {
        this.name = name;
        this.weighings = weighings;
    }

    @Delegate
    public Map<LocalDate, Integer> getWeighings() {
        return weighings;
    }

    @Log
    public boolean addWheight(LocalDate date, int weight) {
        boolean exists = weighings.containsKey(date);
        weighings.put(date, weight);
        return exists;
    }

    @Delegate
    @Override
    public String toString() {
        return "Pearson{" +
                "name='" + name + '\'' +
                ", weighings=" + weighings +
                '}';
    }
}
