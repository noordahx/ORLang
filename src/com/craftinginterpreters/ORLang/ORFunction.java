package com.craftinginterpreters.ORLang;
import java.util.List;
public class  ORFunction implements ORCallable {
    private final String name;
    private final Expr.Function function;
    private final Environment closure;
    private final boolean isInitializer;

    public ORFunction(String name, Expr.Function function, Environment closure, boolean isInitializer)
    {
        this.name = name;
        this.function = function;
        this.closure = closure;
        this.isInitializer = isInitializer;
    }

    ORFunction bind(ORInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new ORFunction(name, function, environment, isInitializer);
    }


    @Override
    public int arity() {
        return function.parameters.size();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for (int i = 0; i < function.parameters.size(); i++) {
            environment.define(function.parameters.get(i).lexeme,
                    arguments.get(i));
        }

        try {
            interpreter.executeBlock(function.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }
        if (isInitializer) return closure.getAt(0, "this");
        return null;
    }

    @Override
    public String toString() {
        if (name == null) return "<fn>";
        return "<fn " + name + ">";
    }
}
