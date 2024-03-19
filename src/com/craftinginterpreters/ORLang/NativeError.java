package com.craftinginterpreters.ORLang;

public class NativeError extends RuntimeException {
    public NativeError(String s) {
        super(s);
    }
}
