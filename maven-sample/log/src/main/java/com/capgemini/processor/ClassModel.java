package com.capgemini.processor;

import com.capgemini.processor.method.Constructor;
import com.capgemini.processor.method.Method;

import java.util.List;
import java.util.Objects;

public class ClassModel {
    private final String pkg;

    private final String name;
    private final List<Method> methods;
    private final List<Constructor> constructors;

    public ClassModel(String pkg, String name, List<Method> methods, List<Constructor> constructors) {
        this.pkg = pkg;
        this.name = name;
        this.methods = methods;
        this.constructors = constructors;
    }

    public String getName() {
        return name;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public List<Constructor> getConstructors() {
        return constructors;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassModel that = (ClassModel) o;
        return Objects.equals(pkg, that.pkg) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pkg, name);
    }
}
