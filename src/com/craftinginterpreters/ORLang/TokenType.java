package com.craftinginterpreters.ORLang;

enum TokenType {
    // Single-character tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS, PLUS, COLON, SEMICOLON, SLASH, STAR, HASH,

    // One or two character tokens
    BANG, BANG_EQUAL, EQUAL, EQUAL_EQUAL, GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL, QUESTION_MARK,

    // Literals
    IDENTIFIER, STRING, NUMBER,

    // Keywords
    AND,BREAK, CLASS, ELSE, FALSE, FUN, FOR, IF, NIL, OR, PRINT,
    RETURN, SUPER, THIS, TRUE, VAR, WHILE,

    EOF
}
