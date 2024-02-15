```agsl
--------------------------------------------------

--- ORLang - inspired by Robert Nystrom's jLox ---

--------------------------------------------------

-- Tree-walk interpreter directly executing AST --

Parser's grammar rules:
program     -> declaration* EOF ;

declaration -> varDecl | statement ;

varDecl     -> "var" IDENTIFIER ("=" expression)? ";" ;

statement   -> exprStmt | printStmt ;

exprStmt    -> expresssion ";" ;
printStmt   -> "print" expression ";" ;

expression  -> comma ;
comman      -> ternary ( ( "," ) ternary )* ;
ternary     -> equality ( "?" expression ":" expression )* ;
equality    -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factor      -> unary ( ( "/" | "*" ) factor )* ;
unary       -> ( "!" | "-" ) unary | primary ;
primary     -> NUMBER | STRING | "true" | "false" | "nil"
            | "(" expression ")"
            | IDENTIFIER ;


where   * - multiple
        ? - at most once
        
``` 



