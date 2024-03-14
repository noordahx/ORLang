package com.craftinginterpreters.ORLang;

import java.util.HashMap;
import java.util.Map;

public class ORInstance {
    private ORClass klass;
    private final Map<String, Object> fields = new HashMap<>();

    ORInstance(ORClass klass) {
        this.klass = klass;
    }

    Object get(Token name) {
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }

        ORFunction method = klass.findMethod(name.lexeme);
        if (method != null) return method.bind(this);

        throw new RuntimeError(name, String.format("Undefined property %s.", name.lexeme));
    }

    void set(Token name, Object value) {
        fields.put(name.lexeme, value);
    }

    @Override
    public String toString() {
        return klass.name + " instance";
    }
}
