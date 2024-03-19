package com.craftinginterpreters.ORLang;

import java.util.List;
import java.util.Map;

public class ORClass implements ORCallable {
    final String name;
    final ORClass superclass;
    private final Map<String, ORFunction> methods;

    ORClass(String name, ORClass superclass, Map<String, ORFunction> methods) {
        this.superclass = superclass;
        this.name = name;
        this.methods = methods;
    }

    ORFunction findMethod(ORInstance instance, String name) {
        if (methods.containsKey(name)) {
            return methods.get(name).bind(instance);
        }
        if (superclass != null) {
            return superclass.findMethod(instance, name);
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int arity() {
        ORFunction initializer = methods.get("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        ORInstance instance = new ORInstance(this);
        ORFunction initializer = methods.get("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }
}
