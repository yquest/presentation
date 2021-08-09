package com.capgemini.processor.method;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Method {
    private final List<Parameter> parameters;
    private final String returnType;
    private final List<String> exceptions;
    private final String name;
    private final boolean voidReturn;
    private final boolean log;
    private final String visibility;

    public Method(String visibility, List<Parameter> parameters, String returnType, List<String> exceptions, String name, boolean log) {
        this.visibility = visibility;
        this.parameters = parameters;
        this.returnType = returnType;
        this.exceptions = exceptions;
        this.name = name;
        voidReturn = returnType.equals("void");
        this.log = log;
    }

    public String getVisibility() {
        return visibility;
    }

    public String getParametersRendered() {
        return parameters.stream().map(e -> e.getType() + " " + e.getName())
                .collect(Collectors.joining(", "));
    }

    public String getName() {
        return name;
    }

    public String getExceptionsRendered() {
        if (exceptions.isEmpty()) return "";
        else return "throws " + String.join(", ", exceptions);
    }

    public List<Parameter> getParameters() {
        return parameters;
    }

    public List<String> getExceptions() {
        return exceptions;
    }

    public String getReturnType() {
        return returnType;
    }

    public List<String> getDelegationRendered() {
        String returnexp;
        if (voidReturn) returnexp = "";
        else if (log) returnexp = returnType + " $result = ";
        else returnexp = "return ";
        List<String> list = new ArrayList<>();
        if (log) {
            list.add("System.out.println(\"called method "+name+"\");");
            list.add("System.out.println(\"  \" + " + parameters.stream().map(e -> "\"" + e.getName() + "=\" + String.valueOf(" + e.getName() + ")").collect(Collectors.joining("+\",\"+")) + ");");
        }
        list.add(returnexp + "this.delegate." + name + "(" + parameters.stream().map(Parameter::getName).collect(Collectors.joining(", ")) + ");");
        if (!voidReturn && log) {
            list.add("System.out.println(\"  result=\" + $result);");
            list.add("return $result;");
        }
        return list;
    }
}
