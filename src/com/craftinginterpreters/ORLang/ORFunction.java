package com.craftinginterpreters.ORLang;
import java.util.List;
public class ORFunction implements ORCallable {
    private final String name;
    private final Stmt.Function declaration;
    private final Environment closure;
    private final Expr.Function exprFunction;

    public ORFunction(Stmt.Function declaration, Environment closure)
    {
        if (declaration.name != null) {
            this.name = declaration.name.lexeme;
        } else {
            this.name = null;
        }
        this.declaration = declaration;
        this.exprFunction = declaration.function;
        this.closure = closure;
    }

    ORFunction bind(ORInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new ORFunction(declaration, environment);
    }


    @Override
    public int arity() {
        return declaration.function.parameters.size();
    }

    @Override
    public Object call(Interpreter interpreter,
                       List<Object> arguments) {
        Environment environment = new Environment(interpreter.globals);
        for (int i = 0; i < exprFunction.parameters.size(); i++) {
            environment.define(exprFunction.parameters.get(i).lexeme,
                    arguments.get(i));
        }

        try {
            interpreter.executeBlock(exprFunction.body, environment);
        } catch (Return returnValue) {
            return returnValue.value;
        }

        return null;
    }

    @Override
    public String toString() {
        if (name == null) return "<fn>";
        return "<fn " + name + ">";
    }
}
