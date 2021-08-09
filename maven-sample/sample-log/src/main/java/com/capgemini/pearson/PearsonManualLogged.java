package com.capgemini.pearson;

import java.time.LocalDate;
import java.util.Map;

public class PearsonManualLogged {
    private final Pearson delegate;

    public PearsonManualLogged(java.lang.String name){
        this.delegate = new Pearson(name);
    }

    public PearsonManualLogged(java.lang.String name, Map<LocalDate, Integer> weighings){
        System.out.println("called constructor");
        System.out.println("  name=" + name +","+"weighings=" + weighings);
        this.delegate = new Pearson(name , weighings);
    }

    public Map<LocalDate, Integer> getWeighings (){
        return this.delegate.getWeighings();
    }

    public java.lang.String toString (){
        return this.delegate.toString();
    }

    public boolean addWheight (LocalDate date, int weight){
        System.out.println("called method addWheight");
        System.out.println("  date=" + date +","+"weight=" + weight);
        boolean result = this.delegate.addWheight(date, weight);
        System.out.println("  result=" + result);
        return result;
    }
}
