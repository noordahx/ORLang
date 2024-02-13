package com.craftinginterpreters.ORLang;
import java.util.ArrayList;
import java.util.List;
import static com.craftinginterpreters.ORLang.TokenType.*;
public class Parser {
    private static class ParseError extends RuntimeException {}

    private static class UnhandledParseError extends RuntimeException {}
    private static class HandledParseError {
        HandledParseError(Token errorPoint, String message) {
            this.errorPoint = errorPoint;
            this.message = message;
        }
        final Token errorPoint;
        final String message;
    }

    private List<HandledParseError> handledParseErrors = new ArrayList<>();

    private final List<Token> tokens;
    private int current = 0;

    Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    Expr parse() {
        try {
            return expression();
//            for (HandledParseError errPdxn : this.handledParseErrors) {
//                ORLang.error(errPdxn.errorPoint, errPdxn.message);
//            }
        } catch (ParseError error) {
            return null;
        }

    }

    private Expr expression() {
        return comma();
    }

    private Expr comma() {
        Expr expr = ternary();
        while(match(COMMA)) {
            Token operator = previous();
            Expr right = ternary();

            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr ternary() {
        Expr expr = equality();
        while (match(QUESTION_MARK)) {
            Expr trueStatement = equality();
            consume(COLON, "expected : after expression");
            Expr falseStatement = equality();

            expr = new Expr.Condition(expr, trueStatement, falseStatement);
        }

        return expr;
    }

    private Expr equality() {
        Expr expr = comparison();

        while (match(BANG_EQUAL, EQUAL_EQUAL)) {
            expr = checkForMissingExpression(expr, "Equality operators must have a left and right operand.");
            Token operator = previous();
            Expr right = comparison();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr comparison() {
        Expr expr = term();

        while (match(GREATER, GREATER_EQUAL, LESS, LESS_EQUAL)) {
            expr = checkForMissingExpression(expr, "Comparison operators must have a left and right operand.");
            Token operator = previous();
            Expr right = term();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr term() {
        Expr expr = factor();

        while (match(MINUS, PLUS)) {
            expr = checkForMissingExpression(expr, "Binary operators must have a left and right operand.");
            Token operator = previous();
            Expr right = factor();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr factor() {
        Expr expr = unary();

        while (match(SLASH, STAR)) {
            expr = checkForMissingExpression(expr, "Binary operators must have a left and right operand.");
            Token operator = previous();
            Expr right = unary();
            expr = new Expr.Binary(expr, operator, right);
        }

        return expr;
    }

    private Expr unary() {
        if (match(BANG, MINUS)) {
            Token operator = previous();
            Expr right = unary();
            return new Expr.Unary(operator, right);
        }

        return primary();
    }

    private Expr primary() {
        if (match(FALSE)) return new Expr.Literal(false);
        if (match(TRUE)) return new Expr.Literal(true);
        if (match(NIL)) return new Expr.Literal(null);

        if (match(NUMBER, STRING)) {
            return new Expr.Literal(previous().literal);
        }

        if (match(LEFT_PAREN)) {
            Expr expr = expression();
            consume(RIGHT_PAREN, "Expected ')' after expression.");
            return new Expr.Grouping(expr);
        }

//        throw error(peek(), "Expect expression");
        return new Expr.Nothing("Nothing here.");
    }

    private boolean match(TokenType... types) {
        for (TokenType type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type, String message) {
        if (check(type)) return advance();

        throw error(peek(), message);
    }

    private boolean check(TokenType type) {
        if (isAtEnd()) return false;
        return peek().type == type;
    }

    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().type == EOF;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private Expr checkForMissingExpression(Expr expr, String errorMessage) {
        if (expr instanceof Expr.Nothing) {
            HandledParseError error = new HandledParseError(previous(), errorMessage);
            this.handledParseErrors.add(error);
        }
        return expr;
    }

    private ParseError error(Token token, String message) {
        ORLang.error(token, message);
        return new ParseError();
    }

    // discard tokens until erroneous statement's boundary
    private void synchronize() {
        advance();

        while (!isAtEnd()) {
            if (previous().type == SEMICOLON) return;

            switch (peek().type) {
                case CLASS:
                case FUN:
                case VAR:
                case FOR:
                case IF:
                case WHILE:
                case PRINT:
                case RETURN:
                    return;
            }

            advance();
        }
    }
}
