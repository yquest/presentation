package com.capgemini.processor.method;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Constructor {
    private final String visibility;
    private final List<Parameter> parameters;
    private final List<String> exceptions;
    private final boolean log;

    public Constructor(String visibility, List<Parameter> parameters, List<String> exceptions, boolean log) {
        this.visibility = visibility;
        this.parameters = parameters;
        this.exceptions = exceptions;
        this.log = log;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getParametersRendered() {
        return parameters.stream().map(e -> e.getType() + " " + e.getName())
                .collect(Collectors.joining(", "));
    }

    public String getExceptionsRendered() {
        if (exceptions.isEmpty()) return "";
        else return "throws " + String.join(", ", exceptions);
    }

    public List<String> getDelegationRendered(String name) {
        List<String> list = new ArrayList<>();
        if (log && !parameters.isEmpty()) {
            list.add("System.out.println(\"called constructor\");");
            list.add("System.out.println(\"  \"+" + parameters.stream().map(e -> "\"" + e.getName() + "=\" + String.valueOf(" + e.getName() + ")").collect(Collectors.joining("+\",\"+")) + ");");
        }
        list.add("this.delegate = new " + name + "(" + parameters.stream().map(Parameter::getName).collect(Collectors.joining(" , ")) + ");");
        return list;
    }
}
