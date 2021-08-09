package com.capgemini.db.processor;

import java.util.Map;

public class DBEntries {
    private final Map<String,String> map;

    public DBEntries(Map<String,String> map) {
        this.map = map;
    }

    public Map<String, String> getMap() {
        return map;
    }
}