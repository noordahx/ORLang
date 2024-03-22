```

--------------------------------------------------
------------- Tree-walk interpreter --------------
--------------------------------------------------

Structure:
  src
    └── com
        └── craftinginterpreters
            ├── ORLang
            │   ├── AstPrinter.java
            │   ├── Environment.java
            │   ├── Expr.java
            │   ├── Interpreter.java
            │   ├── ORCallable.java
            │   ├── ORFunction.java
            │   ├── ORLang.java
            │   ├── Parser.java
            │   ├── Return.java
            │   ├── RuntimeError.java
            │   ├── Scanner.java
            │   ├── Stmt.java
            │   ├── Token.java
            │   └── TokenType.java
            └── Tool
                └── GenerateAst.java

Logic:
Scanner -> Parser -> Resolver -> Interpreter

--------------------------------------------------

Parser's grammar rules:
program     -> declaration* EOF ;

declaration -> classDecl  
            | funDecl 
            | varDecl 
            | statement ;

classDecl   -> "class" IDENTIFIER ( "<" IDENTIFIER ) ? "{" function* "}" ;

funDecl     -> "fun" function;

varDecl     -> "var" IDENTIFIER ("=" expression)? ";" ;

statement   -> exprStmt
            | forStmt
            | ifStmt 
            | printStmt
            | whileStmt
            | block ;

function    -> IDENTIFIER "(" parameters? ")" block ;

parameters  -> IDENTIFIER ( "," IDENTIFIER )* ;

forStmt     -> "for" "(" ( varDecl | exprStmt | ";")
                expression? ";"
                expression? ")" statement;            

returnStmt  -> "return" expression? ";";

whileStmt   -> "while" "(" expression ")" statement ;

ifStmt      -> "if" "(" expression ")" statement
                ( "else" statement )? ;

block       -> "{" declaration "}" ;

exprStmt    -> expression ";" ;

printStmt   -> "print" expression ";" ;

expression  -> assignment ;

assignment  -> ( call "." ) ? IDENTIFIER "=" assignment 
            | logic_or ;


logic_or    -> logic_and ( "or" logic_and )* ;
logic_and   -> equality ( "and" equality )* ;

 
equality    -> comparison ( ( "!=" | "==" ) comparison )* ;
comparison  -> term ( ( ">" | ">=" | "<" | "<=" ) term )* ;
term        -> factor ( ( "-" | "+" ) factor )* ;
factor      -> unary ( ( "/" | "*" ) factor )* ;
unary       -> ( "!" | "-" ) unary | call ;
call        -> primary ( "(" arguments? ")" | "." IDENTIFIER )* ;
arguments   -> expression ( "," expression )* ;
primary     -> NUMBER | STRING | "true" | "false" | "nil"
            | "(" expression ")"
            | IDENTIFIER 
            | "super" "." IDENTIFIER ;


where   * - multiple
        ? - at most once i.e. 0 or 1


not in use:

logic_and   -> comma ( "and" comma )* ;
comma       -> ternary ( ( "," ) ternary )* ;
ternary     -> equality ( "?" expression ":" expression )* ;
```
