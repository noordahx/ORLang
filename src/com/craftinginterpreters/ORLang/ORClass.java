package com.craftinginterpreters.ORLang;

import java.util.List;
import java.util.Map;

public class ORClass implements ORCallable {
    final String name;
    private final Map<String, ORFunction> methods;
    ORClass(String name, Map<String, ORFunction> methods) {
        this.name = name;
        this.methods = methods;
    }

    ORFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }

        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int arity() {
        return 0;
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        ORInstance instance = new ORInstance(this);
        return instance;
    }
}
