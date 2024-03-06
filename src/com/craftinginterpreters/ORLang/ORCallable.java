package com.craftinginterpreters.ORLang;

import java.util.List;

interface ORCallable {
    Object call(Interpreter interpreter, List<Object> arguments);
}
