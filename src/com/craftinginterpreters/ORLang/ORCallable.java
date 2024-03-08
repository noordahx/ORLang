package com.craftinginterpreters.ORLang;

import java.util.List;

interface ORCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}
