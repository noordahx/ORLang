```
--------------------------------------------------

--- ORLang - inspired by Robert Nystrom's jLox ---

--------------------------------------------------

ORLang java implementation (jlox):
└── ORLang
    └── com
        └── craftinginterpreters
            ├── ORLang
            │   ├── AstPrinter.class
            │   ├── Expr.class
            │   ├── Interpreter.class
            │   ├── ORLang.class
            │   ├── Parser.class
            │   ├── RuntimeError.class
            │   ├── Scanner.class
            │   ├── Stmt.class
            │   ├── Token.class
            │   └── TokenType.class
            └── Tool
                └── GenerateAst.class



-- Tree-walk interpreter directly executing AST --

Parser's grammar rules:
program     -> declaration* EOF ;

declaration -> classDecl  
            | funDecl 
            | varDecl 
            | statement ;

classDecl   -> "class" IDENTIFIER "{" function* "}" ;

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
            | IDENTIFIER ;


where   * - multiple
        ? - at most once


not in use:

// logic_and   -> comma ( "and" comma )* ;
// comma       -> ternary ( ( "," ) ternary )* ;
// ternary     -> equality ( "?" expression ":" expression )* ;
```